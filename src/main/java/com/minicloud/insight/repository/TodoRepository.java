package com.minicloud.insight.repository;

import com.minicloud.insight.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository를 상속받으면 기본 메서드 자동 제공:
    // - save(todo)           → INSERT/UPDATE
    // - findById(id)         → SELECT WHERE id = ?
    // - findAll()            → SELECT * FROM todos
    // - deleteById(id)       → DELETE WHERE id = ?
    // - count()              → SELECT COUNT(*)

    List<Todo> findByCompleted(Boolean completed);
    // SELECT * FROM todos WHERE completed = ?

    List<Todo> findByTitleContaining(String title);
    // SELECT * FROM todos WHERE title LIKE %?%

}
