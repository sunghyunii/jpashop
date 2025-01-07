package jpabook.jpashop.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embeddable  //어딘가에 내장될 수 있다
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //jpa가 reflection 이나 proxy 기술을 쓸 때 기본 생성자가 없으면 못하기 때문에 만들어야한다
    protected Address(){}

    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
