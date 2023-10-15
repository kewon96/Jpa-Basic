package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;


// JPA 내부적으로 리플렉션을 쓰기때문에 기본생성자가 있어야한다.(public일 필요는 없음)
@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
@Builder
public class Member {
    @Id // 최소한 Key가 무엇인지 알려줘야한다.
    private Long id;
    private String name;
    private LocalDateTime time;

    public static Member random() {
        long id = (long) ( Math.random() * 100000 );
        String name = createName();

        System.out.println("id = " + id);
        System.out.println("name = " + name);

        return Member.builder()
                .id(id)
                .name(name)
                .time(LocalDateTime.now())
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
