package code.club.blog.controller;

import code.club.blog.model.Post;
import code.club.blog.service.BlogUserService;
import code.club.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        this.postService = postService;
        this.blogUserService = blogUserService;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Post> optionalPost = this.postService.getById(id);
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
    public String createNewPost(Model model, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<BlogUser> optionalBlogUser = this.blogUserService.findByUsername(authUsername);
        if (optionalBlogUser.isPresent()) {
            Post post = new Post();
            post.setUser(optionalBlogUser.get());
            model.addAttribute("post", post);
            return "postForm";
        } else {
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/createNewPost")
    public String createNewPost(@Valid @ModelAttribute Post post, BindingResult bindingResult, SessionStatus sessionStatus) {
        System.err.println("POST post: " + post);
        if (bindingResult.hasErrors()) {
            System.err.println("Post did not validate");
            return "postForm";
        }
        this.postService.save(post);
        System.err.println("SAVE post: " + post);
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

        Optional<Post> optionalPost = this.postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("post", post);
                System.err.println("EDIT post: " + post);
                return "postForm";
            } else {
                System.err.println("Current User has no permissions to edit anything on post by id: " + id);
                return "403";
            }
        } else {
            System.err.println("Could not find a post by id: " + id);
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }


        Optional<Post> optionalPost = this.postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (authUsername.equals(post.getUser().getUsername())) {
                this.postService.delete(post);
                System.err.println("DELETED post: " + post);
                return "redirect:/";
            } else {
                System.err.println("Current User has no permissions to edit anything on post by id: " + id);
                return "403";
            }
        } else {
            System.err.println("Could not find a post by id: " + id);
            return "error";
        }
    }
}
