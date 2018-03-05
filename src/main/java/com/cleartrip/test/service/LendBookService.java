package com.cleartrip.test.service;


import com.cleartrip.test.entity.BookEntity;
import com.cleartrip.test.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static java.util.Objects.nonNull;

@Controller
public class LendBookService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    private static int MAX_BORROW_SIZE = 2;


    public void lendBook(String uid, String bid) {


        UserEntity userE = userService.getUserEntity(uid);
        BookEntity bookE = bookService.getBookEntity(bid);

        if (nonNull(userE)) {
            if (userE.getBid().size() <= MAX_BORROW_SIZE) {
                if (nonNull(bookE) && bookE.getCount() > 0) {
//                           update user info
                    userService.updateUser(userE.getEmailId(), userE, bookE.getName());
//                update book info
                    bookService.updateBook(bookE.getName(), bookE);

                } else {
                    System.out.println("selecting book is not present in the system");
                }
            } else {
                System.out.println(" User reached to max limit");
            }
        } else {
            System.out.println(" User must add in the system");
        }
    }
}
