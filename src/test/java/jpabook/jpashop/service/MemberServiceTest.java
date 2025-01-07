package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.JpashopApplication;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
//아래 두가지가 있어야 스프링이랑 통합해서 스프링 부트를 실제 올려서
//테스트 할 수 있다
//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = JpashopApplication.class)
//스프링에서 트랜잭션은 rollback을 한다
@Transactional
public class MemberServiceTest {
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;
    @Test
    //롤백하지 않고 커밋
    //롤백을 해버리는 것은 DB에서 다 버린다는 뜻이기 때문에 insert 문 조차 나가지 않는다
    //영속성 컨텍스트에 flush 하지 않는다
    public void 회원가입() throws Exception{
        //given(이런게 주어졌을 떄)
        Member member = new Member();
        member.setName("Kim");

        //when(이렇게 하면)
        //db 마다 차이는 있겠찌만 기본적으로 persist 하면 insert 문이 나가지 않음
        Long saveId = memberService.join(member);

        //then(이렇게 된다)
        //리포지토리에서 찾아온 member와 member 객체가 같은지 확인
        //JPA에서 같은 트랜잭션 안에서 같은 엔티티, PK 값이 같으면
        // 같은 영속성 컨텍스트에서 관리한다
        //DB에 강제로 나간다
        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외(){
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        memberService.join(member2);

        fail("에외가 발생해야 한다."); //여기오면 안된다

    }

}