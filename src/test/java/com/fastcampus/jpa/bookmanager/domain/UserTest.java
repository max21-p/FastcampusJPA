package com.fastcampus.jpa.bookmanager.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void test() {
        User user = new User();
        user.setEmail("max@naver.com");
        user.setName("max");

        System.out.println(">>> " + user);
    }

}