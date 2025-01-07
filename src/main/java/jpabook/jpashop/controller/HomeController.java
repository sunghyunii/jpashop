package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
//로그를 찍어볼 수 있다
@Slf4j
public class HomeController {

    //첫 번째 화면에 여기로 잡힌다
    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        //home.html로 찾아가서 타임 리프 파일을 찾는다
        return "home";
    }
}




