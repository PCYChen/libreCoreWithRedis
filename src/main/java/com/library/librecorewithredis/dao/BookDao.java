package com.library.librecorewithredis.dao;

import com.library.librecorewithredis.dto.BookRequest;
import com.library.librecorewithredis.model.Book;

public interface BookDao {

    Book getBookById(Integer bookId);

    Integer createBook(BookRequest bookRequest);

    void updateBook(Integer bookId, BookRequest bookRequest);

    void deleteBookById(Integer bookId);
}
