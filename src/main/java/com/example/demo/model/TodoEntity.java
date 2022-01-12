package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // 자바 클래스를 엔티티로 지정 @Entity("TodoEntity") : 엔티티에 이름을 부여
@Table(name = "Todo")  // 해당 엔티티는 데이터베이스의 Todo 테이블에 매핑된다는 의미
// @Table 을 추가하지 않거나, name 속성을 작성 X > @Entity 의 이름을 테이블 이름으로 간주
// @Entity 에 이름을 지정하지 않은 경우 > 클래스 이름을 테이블 이름으로 간주
public class TodoEntity {

    @Id // 기본키가 될 필드에 지정
    @GeneratedValue(generator = "system-uuid")  // 자동으로 생성 (generator : @GenericGenerator 에 정의된 이름)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")  // 나만의 Generator 사용
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
