package com.Ylb.test;

import com.Ylb.dao.impl.bookDao;
import com.Ylb.dao.impl.impl.BookDaoImpl;
import com.Ylb.pojo.Book;
import com.Ylb.pojo.Page;
import org.junit.Test;

import java.math.BigDecimal;

public class bookDaoTest {
    private bookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"杨凌波养成记","惊蛰",new BigDecimal(9999),1000,0,null));
    }

    @Test
    public void delete_book_by_id() {
        bookDao.delete_book_by_id(21);
    }

    @Test
    public void update_book() {
        bookDao.update_book(new Book(21,"杨凌波豪杰物语","惊s蛰",new BigDecimal(99199),10010,3,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(21));
    }

    @Test
    public void queryBooks() {
        System.out.println(bookDao.queryBooks());
    }
    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }
    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10,50));
    }
    @Test
    public void queryForPageItems() {
       for (Book book: bookDao.queryForPageItems(8, Page.page_size)){
           System.out.println(book);
       }
    }
    @Test
    public void queryForPageItemsByPrice() {
        for (Book book: bookDao.queryForPageItemsByPrice(0, Page.page_size,10,50)){
            System.out.println(book);
        }
    }
}