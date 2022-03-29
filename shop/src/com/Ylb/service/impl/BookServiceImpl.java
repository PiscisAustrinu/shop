package com.Ylb.service.impl;

import com.Ylb.dao.impl.bookDao;
import com.Ylb.dao.impl.impl.BookDaoImpl;
import com.Ylb.pojo.Book;
import com.Ylb.pojo.Page;
import com.Ylb.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private bookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.delete_book_by_id(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update_book(book);
    }

    @Override
    public Book queryBookById(Integer id) {

        return (Book) bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<Book>();

//        设置每页显示最大数据条数
        page.setPageSize(pageSize);
//        设置总数据数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);
//        设置总页数
        Integer pageTotal = pageTotalCount/pageSize;
        if (pageTotalCount%pageSize>0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);
//      设置当前页码
        page.setPageNo(pageNo);
//      设置索引
        int begin = (page.getPageNo()-1)*pageSize;
        List<Book> items = bookDao.queryForPageItems(begin,pageSize);
        page.setItems(items);
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<Book>();

//        设置每页显示最大数据条数
        page.setPageSize(pageSize);
//        设置总数据数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min,max);
        page.setPageTotalCount(pageTotalCount);
//        设置总页数
        Integer pageTotal = pageTotalCount/pageSize;
        if (pageTotalCount%pageSize>0){
            pageTotal++;
        }
        page.setPageTotal(pageTotal);
//      设置当前页码
        page.setPageNo(pageNo);
//      设置索引
        int begin = (page.getPageNo()-1)*pageSize;
        List<Book> items = bookDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        page.setItems(items);
        return page;
    }
}
