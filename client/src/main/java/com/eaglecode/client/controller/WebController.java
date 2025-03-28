//package com.eaglecode.client.controller;
//
//import com.eaglecode.client.dto.RegistrationRequest;
//import com.eaglecode.client.model.Book;
//import com.eaglecode.client.service.AuthService;
//import com.eaglecode.client.service.BookService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@Controller
//@SessionAttributes("token")
//public class WebController {
//    private final AuthService authService;
//    private final BookService bookService;
//
//    public WebController(AuthService authService, BookService bookService) {
//        this.authService = authService;
//        this.bookService = bookService;
//    }
//
//    @GetMapping("/")
//    public String home(@ModelAttribute("token") String token, Model model) {
//        if (token == null) {
//            return "redirect:/login";
//        }
//        return "redirect:/books";
//    }
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String username,
//                        @RequestParam String password,
//                        Model model) {
//        try {
//            String token = authService.login(username, password);
//
//            model.addAttribute("token", token);
//            return "redirect:/books";
//        } catch (Exception e) {
//            model.addAttribute("error", "Invalid credentials");
//            return "login";
//        }
//    }
//
//    @GetMapping("/logout")
//    public String logout() {
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String registrationPage(Model model) {
//        model.addAttribute("registrationRequest", new RegistrationRequest());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String register(@ModelAttribute RegistrationRequest request,
//                           Model model) {
//        try {
//            authService.register(request);
//            return "redirect:/login?registered=true";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "register";
//        }
//    }
//
//    @GetMapping("/books")
//    public String booksPage(@ModelAttribute("token") String token, Model model) {
//        List<Book> books = bookService.getAllBooks(token);
//        model.addAttribute("books", books);
//        return "books";
//    }
//
//    @PostMapping("/books")
//    public String addBook(@ModelAttribute Book book,
//                             @ModelAttribute("token") String token) {
//        bookService.addBook(book, token);
//        return "redirect:/books";
//    }
//
//    @PostMapping("/books/{id}/delete")
//    public String deleteBook(@PathVariable Long id,
//                                @ModelAttribute("token") String token,
//                                RedirectAttributes redirectAttributes) {
//        try {
//            bookService.deleteBook(id, token);
//            redirectAttributes.addFlashAttribute("success", "Book deleted successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/books";
//    }
//
//
//}


package com.eaglecode.client.controller;

import com.eaglecode.client.dto.RegistrationRequest;
import com.eaglecode.client.model.Book;
import com.eaglecode.client.service.AuthService;
import com.eaglecode.client.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import java.util.List;

@Controller
public class WebController {
    private final AuthService authService;
    private final BookService bookService;

    public WebController(AuthService authService, BookService bookService) {
        this.authService = authService;
        this.bookService = bookService;
    }

    // Retrieve JWT from cookies
    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    private boolean isAdmin(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            return payload.contains("\"role\":\"ROLE_ADMIN\"");
        } catch (Exception e) {
            return false;
        }
    }



    @GetMapping("/")
    public String home(HttpServletRequest request) {
        String token = getJwtFromCookies(request);
        if (token == null) {
            return "redirect:/login";
        }
        return "redirect:/books";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        try {
            String token = authService.login(username, password);

            // Store JWT in HttpOnly cookie for security
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);  // Ensure this is only used with HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24); // 1 day expiration
            response.addCookie(cookie);

            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Remove the JWT cookie by setting max age to 0
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/login?logout=true";
    }

    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest request,
                           RedirectAttributes redirectAttributes) {
        try {
            authService.register(request);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "register";
        }
    }

//    @GetMapping("/books")
//    public String booksPage(HttpServletRequest request, Model model) {
//        String token = getJwtFromCookies(request);
//        if (token == null) {
//            return "redirect:/login";
//        }
//        List<Book> books = bookService.getAllBooks(token);
//        model.addAttribute("books", books);
//        return "books";
//    }

    @GetMapping("/books")
    public String booksPage(HttpServletRequest request, Model model) {
        String token = getJwtFromCookies(request);
        if (token == null) {
            return "redirect:/login";
        }
        List<Book> books = bookService.getAllBooks(token);
        model.addAttribute("books", books);
        model.addAttribute("isAdmin", isAdmin(token));
        return "books";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute Book book, HttpServletRequest request) {
        String token = getJwtFromCookies(request);
        if (token == null) {
            return "redirect:/login";
        }
        bookService.addBook(book, token);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/update")
    public String update(@PathVariable Long id,
                         @RequestBody Book book,
                         HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String token = getJwtFromCookies(request);
        if (token == null) {
            return "redirect:/login";
        }
        try {
            bookService.updateBook(id, book, token);
            redirectAttributes.addFlashAttribute("success", "Book updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }


    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        String token = getJwtFromCookies(request);
        if (token == null) {
            return "redirect:/login";
        }

        try {
            bookService.deleteBook(id, token);
            redirectAttributes.addFlashAttribute("success", "Book deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books";
    }
}
