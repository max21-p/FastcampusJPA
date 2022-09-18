package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    void reviewTest() {
        List<Review> reviews = reviewRepository.findAll();
//        List<Review> reviews = reviewRepository.findAllByFetchJoin();
//        List<Review> reviews = reviewRepository.findALlByEntityGraph();


//        System.out.println("전체를 조회합니다");
//
//        System.out.println(reviews.get(0).getComments());
//        System.out.println("첫번째 리뷰를 가져왔습니다");
//
//        System.out.println(reviews.get(1).getComments());
//        System.out.println("두번째 리뷰를 가져왔습니다");
//        System.out.println(reviews);
        reviews.forEach(System.out::println);
    }
}
