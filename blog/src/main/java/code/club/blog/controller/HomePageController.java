package code.club.blog.controller;

import code.club.blog.model.Post;
import code.club.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final PostService postService;

    @Autowired
    public HomePageController(PostService postSerivce) {
        this.postService = postSerivce;
    }

    @GetMapping
    public String displayAllPosts(Model model) {
        Collection<Post> posts = this.postService.getAll();
        model.addAttribute("posts", posts);
        return "home";
    }
}
