package code.club.blog.club.blog.cotroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public String sayHello(){
        return "hello";
    }
    @GetMapping("/bye")
    public String sayBye(){
        return "bye";
    }
    @GetMapping("/GraficCard")
    public String sayTI(){
        return "NVIDIA GeForce RTX 3080 "+ "\n" +"NVIDIA GeForce RTX 3080 ti";
    }

}
