package com.PickOne.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "members")
public class Member {

    // 회원 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long Id;

    // 아이디
    @Column(name = "login_id", nullable = false)
    private String loginId;

    // 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    // 이름
    @Column(name = "username", nullable = false)
    private String username;

    // 이메일
    @Column(name = "email", nullable = false)
    private String email;

    // 별명
    @Column(name = "nickname", nullable = false)
    private String nickname;

    // 역할
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    // 등록자
    @Column(name = "created_by")
    private String createdBy;

    // 수정자
    @Column(name = "updated_by")
    private String updatedBy;

    // 등록일
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 수정일
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateFields(Member source) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(source);
                if (newValue != null) {
                    field.set(this, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("필드 업데이트 중 오류 발생: " + field.getName(), e);
            }
        }
    }
}
