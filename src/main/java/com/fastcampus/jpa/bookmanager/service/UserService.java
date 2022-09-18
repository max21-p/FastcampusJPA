package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void put() {
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@fastcampus.com");

        entityManager.persist(user);
        entityManager.detach(user);     // 준영속상태로 변경(detach)

        user.setName("newUserAfterPersist");
        entityManager.merge(user);      // 준영속상태 이더라도 변경사항을 적용

        entityManager.flush();      // 변경사항을 적용하기 위해 flush 수행(clear 를 하더라도 변경사항 적용을 위해)
        entityManager.clear();      // persist 상태까지는 적용되고, 그 이후는 클리어

        User user1 = userRepository.findById(1L).get();
        entityManager.remove(user1);

        // 이미 지워진 경우 에러 발생
//        user1.setName("martttttin");
//        entityManager.merge(user1);
    }


}
