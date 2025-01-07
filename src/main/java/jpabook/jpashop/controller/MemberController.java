package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import java.util.List;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/members/new")
    public String createForm(Model model) {
        //컨트롤러에서 뷰로 넘길 떄 데이터를 실어서 넘긴다
        //그렇기 때문에 화면에서 MemberForm 객체를 접근할 수 있게 된다
        model.addAttribute("memberForm", new MemberForm());
        //컨트롤러가 화면으로 이동할 때 멤버폼이라는 빈 껍데기를 가지고 간다
        //빈 화면이라 아무것도 없을 수 있는데 벨리데이션을 해주기 때문에 들고간다
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    //@Valid: 스프링이 form에 대해서 있는 어노테이션(NotEmpty, NotBlank, NotNull) 검증
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            //스프링이 BindResult 을 화면까지 끌고 간다
            //어떤 에러가 있는지 다 뿌릴 수 있다
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        //저장되고 재로딩 되거나 하면 안 좋기 때문에 redirect로 폼에 보낸다(첫번째 페이지로 넘어감)
        return "redirect:/";
    }

    @GetMapping("/members")
    public String findMember(Model model){
        List<Member> members = memberService.findMembers();
        //model에 담아서 화면에 뿌린다
        model.addAttribute("members", members);
        return "/members/memberList";
    }
}

