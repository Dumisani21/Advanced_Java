package com.eaglecode.server.service;

import com.eaglecode.server.exception.ResourceNotFoundException;
import com.eaglecode.server.model.Book;
import com.eaglecode.server.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updateBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setAuthor(updateBook.getAuthor());
                    book.setIsbn(updateBook.getIsbn());
                    book.setDescription(updateBook.getDescription());
                    book.setTitle(updateBook.getTitle());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public void deleteBook(Long id) {
        Book student = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        bookRepository.delete(student);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }
}