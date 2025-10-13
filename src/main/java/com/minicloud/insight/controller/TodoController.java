package com.minicloud.insight.controller;

import com.minicloud.insight.domain.Todo;
import com.minicloud.insight.repository.TodoRepository;
import com.minicloud.insight.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping // GET 요청 처리
    public ResponseEntity<List<Todo>> getAllTodos(){
        // service에서 모든 할 일 조회
        List<Todo> todos = todoService.findAll();

        // ResponseEntity: HTTP 응답 전체를 제어
        // - 상태 코드: 200 OK
        // - Body: todos 리스트 (자동으로 JSON 변환)
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        // @PathVariable: URL의 {id} 부분을 파라미터로 받음
        // 예: /api/todos/1 → id = 1
        //     /api/todos/5 → id = 5

        Todo todo = todoService.findById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping // POST 요청 처리
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        // @RequestBody: HTTP 요청 Body의 JSON을 Java 객체로 변환
        // JSON → Todo 객체 (자동 변환)

        Todo created = todoService.create(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody Todo todoDetails
    ){
        Todo update = todoService.update(id, todoDetails);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Todo>> getCompletedTodos(){
        List<Todo> completed = todoService.findByCompleted(true);
        return ResponseEntity.ok(completed);
    }

    @GetMapping("/active")  // GET 요청, URL: /api/todos/active
    public ResponseEntity<List<Todo>> getActiveTodos() {
        // Service를 통해 미완료 할 일만 조회
        List<Todo> active = todoService.findByCompleted(false);
        return ResponseEntity.ok(active);
    }
}
