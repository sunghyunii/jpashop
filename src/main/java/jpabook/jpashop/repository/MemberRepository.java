package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

//컴포넌트 스캔에 의해서 자동으로 스프링 빈으로 관리
@Repository
public class MemberRepository {
    //엔티티 매니저를 인젝션 하기 위해서 @Autowired 대신
    //JPA가 제공하는 표준 어노테이션 @Persistence Context 이 있어야 인젝션이 된다
    //그러나 스프링 부트는 @Autowired도 인젝션되게 지원
    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member);
    }
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }
    public List<Member> findAll(){
        //em.createQuery(JPQL, 반환타입)
        //getResultList(): 리스트로 만들어줌
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
