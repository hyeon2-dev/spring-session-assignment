package com.example.springsessionassignment.todo.controller;

import com.example.springsessionassignment.common.consts.Const;
import com.example.springsessionassignment.todo.dto.*;
import com.example.springsessionassignment.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponseDto> saveTodo(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody TodoRequestDto dto
    ) {
        return ResponseEntity.ok(todoService.saveTodo(memberId, dto));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long todoId){
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<TodoUpdateResponseDto> updateTodo (
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId,
            @RequestBody TodoUpdateRequestDto dto
            ) {
        return ResponseEntity.ok(todoService.updateTodo(memberId, todoId, dto));
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodo(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId
    ) {
        todoService.deleteTodo(memberId, todoId);
    }

}
