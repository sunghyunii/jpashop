package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue //시퀀스 값
    @Column(name="number_id")
    private Long id;
    @NotEmpty
    private String name;

    @Embedded //내장타입
    private Address address;

    @OneToMany(mappedBy = "member") //order 테이블에 있는 "member" 필드에 의해서 매핑
    private List<Order> order = new ArrayList();

}
