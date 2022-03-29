package com.Ylb.dao.impl;
import com.Ylb.pojo.Book;

import java.util.List;

public interface bookDao {
    public int addBook(Book book);
    public int delete_book_by_id(Integer id);
    public int update_book(Book book);
    public Object queryBookById(Integer id);
    public List<Book> queryBooks();

    Integer queryForPageTotalCount();

    List<Book> queryForPageItems(int begin, int pageSize);

    Integer queryForPageTotalCountByPrice(int min, int max);

    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
