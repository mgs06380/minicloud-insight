package com.minicloud.insight.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long Id;
    
    @Column(nullable = false) // NOT NULL
    private String title;
    
    @Column(length = 1000) // 최대 1000자
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;  // 기본값 false

    @CreationTimestamp // 생성 시 자동으로 현재 시간 입력
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp // 수정 시 자동으로 현재 시간 갱신
    private LocalDateTime updatedAt;
}
