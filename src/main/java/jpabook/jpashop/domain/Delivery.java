package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter

public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
    private Address address;
    //EnumType.ORDINAL 이면 1,2 이렇게 숫자로 들어간다
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
