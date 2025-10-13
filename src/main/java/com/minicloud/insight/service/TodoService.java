package com.minicloud.insight.service;

import com.minicloud.insight.domain.Todo;
import com.minicloud.insight.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    // 전체 조회
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    // ID로 조회
    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found: " + id));
    }

    // 생성
    @Transactional
    public Todo create(Todo todo){
        return todoRepository.save(todo);
    }

    // 수정
    @Transactional
    public Todo update(Long id, Todo todoDetails){
        // 1. 존재 여부 확인 (검증 로직)
        Todo todo = findById(id);

        // 2. 필드 값 변경
        // Setter를 사용하여 새로운 값으로 변경
        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.getCompleted());

        return todoRepository.save(todo);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        // 1. 존재 여부 확인 (검증 로직)
        Todo todo = findById(id);

        // 2. 삭제
        todoRepository.delete(todo);
    }

    // 할 일 목록 조회
    @Transactional
    public List<Todo> findByCompleted(Boolean completed){
        return todoRepository.findByCompleted(completed);
    }
}
