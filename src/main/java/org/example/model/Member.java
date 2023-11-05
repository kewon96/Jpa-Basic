package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Random;


// JPA 내부적으로 리플렉션을 쓰기때문에 기본생성자가 있어야한다.(public일 필요는 없음)
@Entity
@Table(name="member")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
@Builder
public class Member {
    @Id // 최소한 Key가 무엇인지 알려줘야한다.
    private Long id;

    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;

    @Lob
    private String description;


    public static Member random() {
        long id = (long) ( Math.random() * 100000 );
        String name = createName();

        return Member.builder()
                .id(id)
                .name(name)
                .createdDate(LocalDateTime.now())
                .age(50)
                .roleType(RoleType.USER)
                .description(String.format("%d / %s", id, name))
                .build();
    }

    private static String createName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
