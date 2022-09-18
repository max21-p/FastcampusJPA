package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long bookId;

    @OneToOne(optional = false)     // null을 허용하지 않음
    private Book book;

    private float averageReviewScore;   // null을 허용하지 않기 위해 프리미티브 타입 사용

    private int reviewCount;    // null을 허용하지 않기 위해 프리미티브 타입 사용


}
