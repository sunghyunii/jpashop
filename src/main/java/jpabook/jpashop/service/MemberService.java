package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//컴포넌트 스캔의 대상이 되어 스프링 빈으로 등록
@Service
@Transactional
//final 필드만 가지고 생성자를 만들어 준다
@RequiredArgsConstructor
public class MemberService {
    //변경할 일이 없기 때문에 필드를 파이널로 하는 것을 권장
    private final MemberRepository memberRepository;
   //한번 생성할 떄 완성되기 때문에 중간에 set해서 바꿀 수 없다
    //최신 버전 스프링에서는 생성자가 딱 하나만 있는 경우에 스프링이 자동으로 인젝션한다 어노테이션 없이

    //가입, 중복확인
    @Transactional
    public long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    //전체 회원 조회 조회
    //읽기에는 readOnly = true 를 넣는 것이 좋다
    //읽기가 아닌 쓰기에는 넣으면 안된다
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    private void validateDuplicateMember(Member member){
        //동시에 같은 이름으로 가입하려고 할 때 ValidateDuplicateMember을 동시에 호출할 수 있다
        //이를 방지하기 위해 실무에서는 DB에 Name을 유니크 제약 조건으로 건다
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
