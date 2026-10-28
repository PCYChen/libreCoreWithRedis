package com.library.librecorewithredis.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.librecorewithredis.dto.BookRequest;
import com.library.librecorewithredis.model.Book;
import com.library.librecorewithredis.service.BookRankingService;
import com.library.librecorewithredis.service.LibraryService;

@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired 
    private BookRankingService bookRankingService;

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Integer bookId) {
        Book book = libraryService.getBookById(bookId);

        bookRankingService.incrementSearchCount(String.valueOf(bookId));

        if (book != null) {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
        Integer bookId = libraryService.createBook(bookRequest);

        Book book = libraryService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer bookId,
                                           @RequestBody BookRequest bookRequest) {
        // 檢查 book 是否存在
        Book book = libraryService.getBookById(bookId);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改 Book 的數據
        Book updatedBook = libraryService.updateBook(bookId, bookRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId) {
        libraryService.deleteBookById(bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/books/hot-searches")
    public ResponseEntity<Set<ZSetOperations.TypedTuple<String>>> getHotSearches( @RequestParam(defaultValue = "10") long limit) {
        Set<ZSetOperations.TypedTuple<String>> topBooks = bookRankingService.getTopSearchBooks(limit);
        return ResponseEntity.status(HttpStatus.OK).body(topBooks);
    }
}
