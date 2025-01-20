package com.PickOne;

import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PickOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickOneApplication.class, args);
    }

    @Bean
    public CommandLineRunner initTestUser(MemberRepository memberRepository) {
        return args -> {
            if (memberRepository.findByLoginId("testUser").isEmpty()) {
                Member testUser = Member.builder()
                        .loginId("testUser")
                        .password("password123") // 기본 비밀번호 설정 (암호화 고려 필요)
                        .username("Test User")
                        .email("testuser@example.com")
                        .nickname("Tester")
                        .build();
                memberRepository.save(testUser);
                System.out.println("Test user created: " + testUser.getLoginId());
            } else {
                System.out.println("Test user already exists.");
            }
        };
    }
}
