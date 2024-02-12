package org.example.a_entity_mapping.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;


// JPA 내부적으로 리플렉션을 쓰기때문에 기본생성자가 있어야한다.(public일 필요는 없음)
//@Entity
//@Table(name="member_1")
//@SequenceGenerator(
//        name = "member_seq_generator",
//        sequenceName = "member_seq",
//        initialValue = 1,
//        allocationSize = 50
//)
//@TableGenerator(
//        name = "member_seq_generator",
//        table = "member_sequence",
//        pkColumnValue = "member_seq",
//        allocationSize = 1
//)
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
@Builder
public class Member1 {

    @Id // 최소한 Key가 무엇인지 알려줘야한다.
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "member_seq_generator")
    private Long id;

    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    @Lob
    private String description;

    public static LinkedList<Member1> createMembers() {
        LinkedList<Member1> resultList = new LinkedList<>();

        for(int i = 0; i < 75; i++) {
            resultList.add(random());
        }

        return resultList;
    }

    public static Member1 random() {
        double random = Math.random();

        long id = (long) ( random * 100000 );
        String name = createName();
        int age = (int) ( random * 100);

        return Member1.builder()
//                .id(id + "")
                .name(name)
                .createdDate(LocalDateTime.now())
                .age(age)
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
