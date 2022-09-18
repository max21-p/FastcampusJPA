package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import com.fastcampus.jpa.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    private final EntityManager entityManager;

    public void put() {
        this.putBookAndAuthor();
    }

//    @Transactional(rollbackFor = Exception.class) // rollbakFor 등록시 Exception 타입에서도 롤백 수행하도록 지정
    @Transactional(propagation = Propagation.REQUIRED)
    // Propagation.REQUIRED : 트랜잭션이 있는 경우 재활용, 따라서 다른 트랜잭션에서 에러 발생시 같이 에러 출력
    // Propagation.REQUIRES_NEW : 트랜잭션을 매번 생성하여 개별로 처리
    // Propagation.NESTED : 호출한 부분의 트랜잭션을 따라 종속적. 상위 메소드는 독립적으로 작동함
    void putBookAndAuthor() {
        Book book = new Book();
        book.setName("JPA 시작하기");

        bookRepository.save(book);

        authorService.putAuthor();
        try {
            throw new RuntimeException("오류 발생 propagation TEST ING.... BookService");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

/*        Author author = new Author();
        author.setName("martin");

        authorRepository.save(author);
        throw new RuntimeException("오류 발생!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");    // 런타임 익셉션은 발생시 롤백이 됨
        // throw new Exception("오류 발생!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");        // 체크 익셉션은 발생시 롤백이 되지 않고 커밋이 됨
        */

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    // Isolation.READ_COMMITTED : 터미널에서 강제커밋한 데이터가 있는 경우 영속성 캐시에 남아있는 데이터에 의해 조회되지 않음
    // entityManager.clear() 를 사용하여 처리필요

    // Isolation.REPEATABLE_READ : 트랜잭션 시작할 당시 데이터를 가지고 있으며, 외부에서 강제로 커밋한 데이터가 있어도 반영하지 않음
    // Repository 클래스에서 @Modifying, @Query(value = "update book set category='none'", nativeQuery = true) 사용하여 처리

    // Isolation.SERIALIZABLE : 최고수준, 커밋이 발생되어야 그 다음 변경사항을 적용할 수 있음
    public void get(Long id) {
        System.out.println(">>> " + bookRepository.findById(id));
        System.out.println(">>> " + bookRepository.findAll());

        entityManager.clear();

        System.out.println(">>> " + bookRepository.findById(id));
        System.out.println(">>> " + bookRepository.findAll());

        bookRepository.update();

        entityManager.clear();

//        Book book = bookRepository.findById(id).get();
//        book.setName("책 제목 변경 테스트");
//        bookRepository.save(book);
    }
}
