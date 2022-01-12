package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // extends  JpaRepository<테이블에 매핑 될 엔티티클래스, 엔티티의 기본 키 타입>
    // JpaRepository : 기본적인 데이터베이스 오퍼레이션 인터페이스 제공
    // 메서드 이름 작성 방법과 예제 : https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    // 메서드 이름을 파싱하여 쿼리 생성
    // SELECT * FROM TodoRepository WHERE userId = '{userId}'
    List<TodoEntity> findByUserId(String userId);

    // 더 복잡한 쿼리는 @Query 어노테이션을 통해 지정
    // ?1 : 메서드의 매개변수의 순서 위치
    @Query(nativeQuery = true, value = "select * from Todo t where t.userId = ?1")
    List<TodoEntity> findByUserIdByQuery(String userId);
}
