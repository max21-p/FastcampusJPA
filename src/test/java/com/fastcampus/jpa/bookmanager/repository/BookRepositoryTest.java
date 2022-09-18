package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import com.fastcampus.jpa.bookmanager.domain.User;
import com.fastcampus.jpa.bookmanager.repository.dto.BookStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest() {
        Book book = new Book();
        book.setName("Jpa 초격차");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional
    void bookRelationTest() {
        givenBookAndReview();

        User user = userRepository.findByEmail("martin@fastcampus.com");

        System.out.println("Review : " + user.getReviews());
        System.out.println("Book : " + user.getReviews().get(0).getBook());
        System.out.println("Publisher : " + user.getReviews().get(0).getBook().getPublisher());
    }

    private void givenBookAndReview() {
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    private User givenUser() {
        return userRepository.findByEmail("martin@fastcampus.com");
    }

    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    private Publisher givenPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");

        return publisherRepository.save(publisher);
    }

    private void givenReview(User user, Book book) {
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("very very Good BOOK");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
    }

    @Transactional
    @Test
    void bookCascadeTest() {
        Book book = new Book();
        book.setName("JPA 캐스케이스 테스트");
//        bookRepository.save(book);

        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스 22");
//        publisherRepository.save(publisher);

        book.setPublisher(publisher);
        bookRepository.save(book);

//        publisher.getBooks().add(book);
//        publisher.addBook(book);
//        publisherRepository.save(publisher);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("slowCampus");

        bookRepository.save(book1);
        System.out.println("publisher22 : " + publisherRepository.findAll());

    }

    @Test
    void bookRemoveCascadeTest() {
        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());

        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));
    }

    @Test
    void queryTest() {
        System.out.println("findByNameRecently : " +
                bookRepository.findByNameRecently(
                        "JPA 초격차 패키지",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)
                ));

        // System.out.println(bookRepository.findBookNameAndCategory());
        bookRepository.findBookNameAndCategory2().forEach(
                b -> System.out.println(
                        b.getName() + " : " + b.getCategory()
                ));

        bookRepository.findBookNameAndCategory2(PageRequest.of(1, 1)).forEach(
                bookNameAndCategory ->
                        System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory())
        );

        bookRepository.findBookNameAndCategory2(PageRequest.of(0, 1)).forEach(
                bookNameAndCategory ->
                        System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory())
        );
    }

    @Test
    void nativeQueryTest() {
//        bookRepository.findAll().forEach(System.out::println);
//        System.out.println("=========================================================");
//        bookRepository.findAllCustom().forEach(System.out::println);

        List<Book> books = bookRepository.findAll();

        for(Book book : books) {
            book.setCategory("IT전문서");
        }

        bookRepository.saveAll(books);
        System.out.println(bookRepository.findAll());

        System.out.println("affected rows : " + bookRepository.updateCategories());
        System.out.println(bookRepository.findAllCustom());
    }

    @Test
    void convertTest() {
        bookRepository.findAll().forEach(System.out::println);

        Book book = new Book();
        book.setName("Other IT 전문서적");
        book.setStatus(new BookStatus(200));

        bookRepository.save(book);

        System.out.println(bookRepository.findRowRecord().values());
    }

}
