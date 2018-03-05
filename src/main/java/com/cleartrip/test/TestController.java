package com.cleartrip.test;


import com.cleartrip.test.pojo.Book;
import com.cleartrip.test.pojo.User;
import com.cleartrip.test.service.BookService;
import com.cleartrip.test.service.LendBookService;
import com.cleartrip.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private LendBookService lendBookService;

    @RequestMapping("/test")
    public String greeting() {
        return "hello World";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/book/add")
    public boolean addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/add")
    public boolean addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/book/all")
    public List<Book> getAllBook() {
        return bookService.getLibrary();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{uid}/books")
    public void lendBooks(@PathVariable("uid") String uid, @PathParam("book") String book) {
        lendBookService.lendBook(uid, book);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/book/{bid}/{uid}")
    public void returnBook(@PathVariable("bid") String bid,@PathVariable("uid") String uid) {
        bookService.updateBookReturn(bid,uid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{eid}")
    public User getUser(@PathVariable("eid") String eid) {
        return userService.getUser(eid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/book/search/{name}")
    public List<Book> searchByNameOrOther(@PathVariable("name") String name){
        return bookService.getBookByNameOrOther(name);
    }
}

