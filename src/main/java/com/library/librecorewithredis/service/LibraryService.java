package com.library.librecorewithredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.library.librecorewithredis.dao.BookDao;
import com.library.librecorewithredis.dto.BookRequest;
import com.library.librecorewithredis.model.Book;

@Component
public class LibraryService {
    
    @Autowired 
    private BookDao bookDao;

    @Cacheable(value = "books", key = "#bookId", unless = "#result == null")
    public Book getBookById(Integer bookId) {
        System.out.println("=== execute query, Book ID: " + bookId + " ===");
        return bookDao.getBookById(bookId);
    }

    public Integer createBook(BookRequest bookRequest) {
        return bookDao.createBook(bookRequest);
    }

    @CachePut(value = "books", key = "#bookId")
    public Book updateBook(Integer bookId, BookRequest bookRequest) {
        bookDao.updateBook(bookId, bookRequest);
        return bookDao.getBookById(bookId);
    }

    @CacheEvict(value = "books", key = "#bookId")
    public void deleteBookById(Integer bookId) {
        bookDao.deleteBookById(bookId);
    }
}
