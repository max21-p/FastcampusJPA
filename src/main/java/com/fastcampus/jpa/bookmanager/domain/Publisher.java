package com.fastcampus.jpa.bookmanager.domain;

import jdk.jpackage.internal.Log;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Publisher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(orphanRemoval = true)        // set메서드를 통해 null을 지정하면 cascade 관계가 끊어지지만, orphanRemoval 설정시 삭제시킴
    @JoinColumn(name = "publisher_id")
    @ToString.Exclude       // failed to lazily initialize a collection of role 에러발생시 toString 연결을 끊는다
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        this.books.add(book);
    }


}
