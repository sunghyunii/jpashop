package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    //이름은 필수로 받을 것이고 나머지는 필수가 아닌 것
    //자바 유효성 검증을 통해서 스프링이 이걸 밸리데이션 해준다
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
