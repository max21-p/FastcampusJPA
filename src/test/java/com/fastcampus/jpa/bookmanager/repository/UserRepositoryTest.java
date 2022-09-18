package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Address;
import com.fastcampus.jpa.bookmanager.domain.Gender;
import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@SpringBootTest
@Transactional      // 각 테스트 메서드에서 처리한 데이터를 메서드 종료시 롤백처리
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    @Transactional
    void crud() {
//        userRepository.save(new User());
//        System.out.println(">>>> "+ userRepository.findAll());
//        userRepository.findAll().forEach(System.out::println);

//        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));

//        List<User> users = userRepository.findAllById(Lists.newArrayList(1L,3L,5L));

//        User user1 = new User("jack", "jack@fastcampus.com");
//        User user2 = new User("test", "test@naver.com");
//        userRepository.saveAll(Lists.newArrayList(user1, user2));
//        List<User> users = userRepository.findAll();

//        User user = userRepository.getOne(1L);  // getOne의 경우 @Transactional 어노테이션 필요
//        System.out.println(user);

//        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));

//        Page<User> users = userRepository.findAll(PageRequest.of(1,3));
//        System.out.println("page : " + users);
//        System.out.println("totalElements : " + users.getTotalElements());
//        System.out.println("totalpages : " + users.getTotalPages());
//        System.out.println("numberOfElements : " + users.getNumberOfElements());
//        System.out.println("sort : " + users.getSort());
//        System.out.println("size : " + users.getSize());
//        users.getContent().forEach(System.out::println);

//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnorePaths("name")
//                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.endsWith());
//        Example<User> example = Example.of(new User("ma", "fastcampus.com"), matcher);
//        userRepository.findAll(example).forEach(System.out::println);

        User user = new User();
        user.setEmail("naver");
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("email", contains());
        Example<User> example = Example.of(user, matcher);
        userRepository.findAll(example).forEach(System.out::println);

    }

    @Test
    void select() {
        System.out.println(userRepository.findByName("martin"));

/*
        System.out.println("findByEmail : " + userRepository.findByEmail("martin@naver.com"));
        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@naver.com", "martin"));
        System.out.println("findByEmailOrName : " + userRepository.findByEmailOrName("martin@naver.com", "max"));
        System.out.println("findByCreatedAtAfter : " + userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByIdAfter : " + userRepository.findByIdAfter(4L));
        System.out.println("findByCreatedAtGreaterThan : " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByCreatedAtGreaterThanEqual : " + userRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByIdBetween : " + userRepository.findByIdBetween(1L, 3L));
        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : " + userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L, 3L));
*/

        System.out.println("findByIdIsNotNull : " + userRepository.findByIdIsNotNull());
//        System.out.println("findByAddressIsNotEmpty : " + userRepository.findByAddressIsNotEmpty());

        System.out.println("findByNameIn : " + userRepository.findByNameIn(Lists.newArrayList("martin", "max")));

        System.out.println("findByNameStartingWith : " + userRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith : " + userRepository.findByNameEndingWith("x"));
        System.out.println("findByNameContains : " + userRepository.findByNameContains("ar"));
        System.out.println("findByNameLike : " + userRepository.findByNameLike("%art%"));

    }

    @Test
    void pagingAndSortingTest() {

/*        System.out.println("findTop1ByName : " + userRepository.findTop1ByName("martin"));
        System.out.println("findTopByNameOrderByIdDesc : " + userRepository.findTopByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + userRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));

        System.out.println("findFirstByName : " + userRepository.findFirstByName("martin"
                , Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));*/

        System.out.println("findByName : " + userRepository.findByName("martin"
                , PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")))));
    }

    @Test
    void enumTest() {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);
        System.out.println(userRepository.findRawRecord().get("gender"));
    }

    @Test
    void preUpdateTest() {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("as-is : " + user);

        user.setName("martin22");
        userRepository.save(user);

        System.out.println("to-be : " + userRepository.findAll().get(0));
    }

    @Test
    void userHistoryTest() {
        User user = new User();
        user.setEmail("martin-new@fastcampus.com");
        user.setName("martin-new");

        userRepository.save(user);

        user.setName("martin-new-new");

        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void userRelationTest() {
        User user = new User();
        user.setName("david");
        user.setEmail("david@fastcampus.com");
        user.setGender(Gender.MALE);

        userRepository.save(user);

        user.setName("daniel");
        userRepository.save(user);

        user.setEmail("daniel@fastcampus.com");
        userRepository.save(user);

//        userHistoryRepository.findAll().forEach(System.out::println);

//        List<UserHistory> result = userHistoryRepository.findByUserId(
//                userRepository.findByEmail("daniel@fastcampus.com").getId());

        List<UserHistory> result = userRepository.findByEmail("daniel@fastcampus.com").getUserHistories();

        result.forEach(System.out::println);

        System.out.println("UserHistory.getUser() : " + userHistoryRepository.findAll().get(0).getUser());
    }

    @Test
    void embedTest() {
        userRepository.findAll().forEach(System.out::println);

        User user = new User();
        user.setName("steve");
        user.setHomeAddress(new Address("서울시", "강남구", "강남대로 364", "06241"));

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);
        userHistoryRepository.findAll().forEach(System.out::println);
    }


}