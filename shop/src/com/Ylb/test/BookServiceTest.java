package com.Ylb.test;

import com.Ylb.pojo.Book;
import com.Ylb.pojo.Page;
import com.Ylb.service.BookService;
import com.Ylb.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class BookServiceTest {
    private BookService bookService = new BookServiceImpl();
    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"abd","sa",new BigDecimal(12121),212,0,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(23);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(23,"abcd","sas",new BigDecimal(1212121),212,0,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(3));
    }

    @Test
    public void queryBooks() {
        System.out.println(bookService.queryBooks());
    }
    @Test
    public void page(){
        System.out.println(bookService.page(1, Page.page_size));
    }
    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(0, Page.page_size,10,50));
    }
}