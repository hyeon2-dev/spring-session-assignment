package com.example.springsessionassignment.todo.service;

import com.example.springsessionassignment.member.entity.Member;
import com.example.springsessionassignment.member.repository.MemberRepository;
import com.example.springsessionassignment.todo.dto.*;
import com.example.springsessionassignment.todo.entity.Todo;
import com.example.springsessionassignment.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoSaveResponseDto saveTodo(Long memberId, TodoRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("id에 맞는 member가 없습니다.")
        );

        Todo todo = new Todo(dto.getContent(), member);
        Todo savedTodo = todoRepository.save(todo);

        return new TodoSaveResponseDto(
                savedTodo.getId(),
                savedTodo.getContent(),
                member.getId(),
                member.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> dtoList = new ArrayList<>();

        for (Todo todo : todos) {
            TodoResponseDto todoResponseDto = new TodoResponseDto(
                    todo.getId(),
                    todo.getContent()
                    );

            dtoList.add(todoResponseDto);
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("id에 맞는 todo가 없습니다.")
        );

        return new TodoResponseDto(
                todo.getId(),
                todo.getContent()
        );
    }

    @Transactional
    public TodoUpdateResponseDto updateTodo(Long memberId, Long todoId, TodoUpdateRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("id에 맞는 member가 없습니다.")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("id에 맞는 todo가 없습니다.")
        );

        todo.updateContent(dto.getContent());

        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getContent()
        );
    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("id에 맞는 member가 없습니다.")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("id에 맞는 todo가 없습니다.")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("todo 작성자가 아닙니다.");
        }

        todoRepository.deleteById(todoId);
    }
}
