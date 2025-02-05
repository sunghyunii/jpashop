package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery*
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for(Order order : all){
            order.getMember().getName();//order.getMember() 까지는 프록시 객체인데 getName하면
                                        //실제 name 을 내가 끌고 와야하기 때문에 Lazy 강제 초기화
        }
        return all;
    }
@GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        //0rder 2개
        //N+1 => 1 + 회원 N + 배송 N
        //쿼리 5개 나감
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o->new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;

    }
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){

        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o->new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;

    }
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderRepository.findOrderDtos();
    }
    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); //Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //Lazy 초기화
        }
    }
}
