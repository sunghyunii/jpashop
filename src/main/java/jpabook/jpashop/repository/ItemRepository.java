package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    //save
    //findOne, findAll
    private final EntityManager em;
    public void save(Item item){
        //id가 없다는 것은 완전히 새로 생성한 객체이다
        if(item.getId() == null){
            //완전히 신규
            em.persist(item);
        }
        else{
            //이미 DB에 등록된 것을 가져온 것이다
            em.merge(item);
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
