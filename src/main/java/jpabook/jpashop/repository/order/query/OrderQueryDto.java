package jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {
    private Long orderId;
    private String name;
    private List<OrderItemQueryDto> orderItems = new ArrayList<>();
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;
    public OrderQueryDto(Long orderId, String name,LocalDateTime orderDate, OrderStatus orderStatus, Address address){
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus=orderStatus;
        this.address=address;
        this.orderItems = orderItems;
    }
    public OrderQueryDto(Long orderId, String name,LocalDateTime orderDate, OrderStatus orderStatus, Address address
    , List<OrderItemQueryDto> orderItems){
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus=orderStatus;
        this.address=address;
        this.orderItems = orderItems;
    }
}
