package com.flashcard.english_card.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    //tạo 1 khóa ngoại tên là user_id trong bảng categories
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //nhiều danh mục thuộc về 1 user
}
