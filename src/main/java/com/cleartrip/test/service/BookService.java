package com.cleartrip.test.service;


import com.cleartrip.test.entity.BookEntity;
import com.cleartrip.test.entity.UserEntity;
import com.cleartrip.test.pojo.Book;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Controller
public class BookService {

    @Autowired
    private UserService userService;

    private Integer count = 0;

    private HashMap<String, BookEntity> libraryMap = new HashMap<>();

    public boolean addBook(Book book) {

        //first check book already exist

        if (nonNull(book) && isNotBlank(book.getName())) {
            if (isNotInLibrary(book)) {
                BookEntity bookEntity = bookToBookEntity(book);
                bookEntity.setCount(bookEntity.getCount() + 1);
                libraryMap.put(book.getName().toLowerCase().trim(), bookEntity);
                return TRUE;
            } else {
                synchronized (count) {
                    BookEntity bookEntity = bookToBookEntity(book);
                    count = bookEntity.getCount() + 1;
                    bookEntity.setCount(count);
                    libraryMap.put(book.getName().toLowerCase().trim(), bookEntity);
                }
            }
            // log here and Throw Exception if reuired
            System.out.println("book Already in the database");
        }
        System.out.println(" input book cannot null or empty");
        return FALSE;
    }

    public List<Book> getLibrary() {
        Iterator<Map.Entry<String, BookEntity>> iterator = libraryMap.entrySet().iterator();

        List<Book> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, BookEntity> next = iterator.next();
            String key = next.getKey();
            BookEntity bookEntity = libraryMap.get(key);
            list.add(mapbookEntityToBook(bookEntity));
        }
        return list;
    }

    private boolean isNotInLibrary(Book book) {
        return libraryMap.get(book.getName().toLowerCase().trim()) == null;
    }

    public BookEntity getBookEntity(String id) {
        if (nonNull(id) && Strings.isNotBlank(id)) {
            BookEntity bookEntity = libraryMap.get(id.toLowerCase().trim());
            if (nonNull(bookEntity)) {
                return bookEntity;
            }
        }
        // log for book not present
        return null;
    }

    public BookEntity updateBook(String id, BookEntity entity) {
        if (nonNull(entity)) {
            synchronized (count) {
                count = entity.getCount() - 1;
                entity.setCount(count);
                libraryMap.put(id, entity);
            }
        }
        return entity;
    }

    public BookEntity updateBookReturn(String id, String uid) {
        BookEntity bookEntity = libraryMap.get(id);
        synchronized (count) {
            count = bookEntity.getCount() + 1;
            bookEntity.setCount(count);
            libraryMap.put(id, bookEntity);
            //update user info here
            UserEntity userE = userService.getUserEntity(uid);
            userService.updateUserReturnBook(id, userE, uid);

        }
        return bookEntity;
    }

    public List<Book> getBookByNameOrOther(String name) {
        return getLibrary().stream().
                filter(l -> {
                    String trim = name.toLowerCase().trim();
                    return l.getName().equals(trim) || l.getAuthor().equals(trim);
                }).
                collect(Collectors.toList());

    }

    private BookEntity bookToBookEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setAuthor(book.getAuthor());
        entity.setName(book.getName());
        return entity;
    }

    private Book mapbookEntityToBook(BookEntity entity) {
        Book book = new Book();
        book.setAuthor(entity.getAuthor());
        book.setName(entity.getName());
        return book;

    }


}
