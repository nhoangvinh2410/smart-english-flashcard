package com.flashcard.english_card.entity;

import com.flashcard.english_card.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private  String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", length = 100)
    private String fullName;
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default //báo cho Builder biết giữ nguyên giá trị mặc định
    private Role role = Role.USER;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true; //true đang hoạt động

    //tự động lấy thời gian hệ thống khi lưu xuống DB
    @CreationTimestamp
    @Column(updatable = false) // Không cho phép sửa ngày tạo
    private LocalDateTime createdAt;
}
