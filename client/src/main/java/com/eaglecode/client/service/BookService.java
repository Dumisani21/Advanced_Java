package com.eaglecode.client.service;

import com.eaglecode.client.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BookService {
    private final WebClient webClient;


    public BookService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Book> getAllBooks(String token) {
        try {
            return webClient.get()
                    .uri("/books")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Book.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            log.error("Error fetching books", e);
            return Collections.emptyList();
        }
    }

    public Book addBook(Book student, String token) {
        try {
            return webClient.post()
                    .uri("/books")
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(student)
                    .retrieve()
                    .bodyToMono(Book.class)
                    .block();
        } catch (Exception e) {
            log.error("Error adding book", e);
            throw new RuntimeException("Failed to add book");
        }
    }

//    public void deleteStudent(Long id, String token) {
//        try {
//            webClient.delete()
//                    .uri("/students/" + id)
//                    .header("Authorization", "Bearer " + token)
//                    .retrieve()
//                    .toBodilessEntity()
//                    .block();
//        } catch (Exception e) {
//            log.error("Error deleting student", e);
//            throw new RuntimeException("Failed to delete student");
//        }
//    }

    public void deleteBook(Long id, String token) {
        try {
            webClient.delete()
                    .uri("/books/" + id)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.Forbidden e) {
            log.error("Access denied when deleting book", e);
            throw new RuntimeException("You don't have permission to delete book");
        } catch (Exception e) {
            log.error("Error deleting book", e);
            throw new RuntimeException("Failed to delete book: " + e.getMessage());
        }
    }

    public Book updateBook(Long id, Book book, String token) {
        try {
            return webClient.put()
                    .uri("/books/" + id)
                    .header("Authorization", "Bearer " + token)
                    .bodyValue(book)
                    .retrieve()
                    .bodyToMono(Book.class)
                    .block();
        } catch (Exception e) {
            log.error("Error updating book", e);
            throw new RuntimeException("Failed to update book");
        }
    }
}