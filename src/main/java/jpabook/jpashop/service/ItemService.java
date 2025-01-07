package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    //위 트랜잭션이 readOnly이기 때문에 저장이 안되기 때문에 오버라이딩
    //매서드와 가까운 것이 우선순위가 높다
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        //save() 호출할 필요 없음 find로 찾아온 findItem은 영속 상태이다
        //그러면 스피링의 transactional에 의해서 트랜잭션이 커밋된다
        //커밋이되면 JPA는 플러쉬를 날린다
        // 플러쉬란 영속성 엔티티 중에 변경된 것을 찾는것
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
    public List<Item> findAll(){
        return itemRepository.findAll();
    }
}
