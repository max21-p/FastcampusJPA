package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional      // 쓰기 지연 발생
public class EntityManagerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void entityManagerTest() {
        System.out.println(entityManager.createQuery("select u from User u").getResultList());
    }

    @Test
    void cacheFindTest() {
//        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println(userRepository.findById(2L));
//        System.out.println(userRepository.findById(2L));
//        System.out.println(userRepository.findById(2L));

        // @Transactional 어노테이션이 있는 경우 수행되지 않음
        userRepository.deleteById(1L);
    }

    @Test
    void cacheFindTest2() {
        User user = userRepository.findById(1L).get();
        user.setName("marrrrrrtin");

        userRepository.save(user);

        System.out.println("=======================");

        user.setEmail("marrrrrrrrrrrtin@fastcampus.com");
        userRepository.save(user);

        // DB에 반영되기 전에 캐시에 저장되어있는 값
        System.out.println(">>> 1. user : " + userRepository.findById(1L).get());

        // @Transactional 이 선언된 경우, 영속성 컨텍스트 내에 캐시로 있어서 한 번의 업데이트만 발생
        // flush() : DB에 값을 반영, 영속성 컨텍스트와 DB 데이터를 동기화시킴
        userRepository.flush();

        // DB SELECT를 하지 않고, 캐시에 남아있는 값을 가져옴
        System.out.println(">>> 2. user : " + userRepository.findById(1L).get());
    }
}
