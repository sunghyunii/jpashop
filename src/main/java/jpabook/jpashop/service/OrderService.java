package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문
     */
    //데이터를 변경하는 것이기 떄문에 @Transactional
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        //주문상품 생성
        //createOrderItem 은 static 생성 매서드
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //주문 생성
        //createOrder 은 static 생성 매서드
        Order order = Order.createOrder(member, delivery, orderItem);
        //주문 저장
        //원래라면 배송정보, 주문 상품은 Repository의 save 매서드를 통해서 저장해야 하지만
        // save 을 하나만 한 이유는 order를 persist 하면 orderItem 도 다 persist 해준다
        //cascade 가 걸려있기 떄문에
        //cascade 범위
        //delivery, orderItem은 order 만 참조해서 쓴다 이와 같은 경우에 써라
        //사이클에 대해서 동일하게 관리를 할 때 의미가 있다
        //만약 delivery가 매우 중요하고 다른데서 참조를 한고 갖다 쓰면 cascade 막 쓰면 안된다
        //왜냐하면 오토 지울 때 저거 다 지워지고 persist도 막 다른게 걸려 있으면 복잡하게 얽혀
        //돌아가기 떄문에 그런 경우에 쓰면 안된다
        orderRepository.save(order);

        return order.getId();
    }
    /**
     * 주문취소
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = (Order) orderRepository.findOne(orderId);
        //주문 취소
        //주문을 취소하면서 orderStatus을 CANCEL로 변경하고 Item 재고를 하나 올렸다
        //원래라면 재고를 하나 더하는 sql을 직접 짜서 올려야 한다
        //JPA 를 활용하면 엔티티 안에 있는 데이터만 바꿔주면 JPA가 알아서 바뀐 변경 포인트들을
        //Dirty Checking(변경 내역 감지)가 일어나면서 변경내역을 찾아서 DB 에 업데이트 쿼리가 날라간다
        order.cancel();
    }

    //검색
    /*
    public List<Order> findOrders(OrderSearch orderSearch){
    return orderRepository.findAllByString(orderSearch);}
*/
}
