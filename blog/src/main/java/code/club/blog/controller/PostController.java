package code.club.blog.controller;

import code.club.blog.model.Post;
import code.club.blog.service.BlogUserService;
import code.club.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("post")
public class PostController {
    private final PostService postService;
    private final BlogUserService blogUserService;

    @Autowired
    public PostController(PostService postService, BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
        this.postService = postService;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("isOwner", true);
            }
            return "post";
        } else {
            return "404";
        }
    }

        @Secured("ROLE_USER")
        @GetMapping("/createNewPost")
        public String createNewPost(@ModelAttribute Post post, BindingResult bindingResult, SessionStatus sessionStatus) {
            if (bindingResult.hasErrors()) {
                System.err.println("POST post " + post);
                return "postForm";
            }
            postService.save(post);
            sessionStatus.setComplete();
            return "redirect:/post/" + post.getId();
        }

    @Secured("ROLE_USER")
    @GetMapping("editPost/{id}")
    public String editPost(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("post", post);
                return "postForm";
            }
            else {
                System.err.println("Current user has no permission to edit the post");
                return "403";
            }
        } else {
            System.err.println("Could not find a post by this id " + id);
            return "error";
        }
    }
}
