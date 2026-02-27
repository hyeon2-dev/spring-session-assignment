package com.example.springsessionassignment.todo.entity;

import com.example.springsessionassignment.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Todo(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
