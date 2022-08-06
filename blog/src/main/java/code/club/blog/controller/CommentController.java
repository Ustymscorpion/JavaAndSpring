package code.club.blog.controller;

import code.club.blog.model.BlogUser;
import code.club.blog.model.Comment;
import code.club.blog.model.Post;
import code.club.blog.service.BlogUserService;
import code.club.blog.service.CommentService;
import code.club.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("comment")
public class CommentController {

    private final PostService postService;
    private final BlogUserService blogUserService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, BlogUserService blogUserService, CommentService commentService) {
        this.postService = postService;
        this.blogUserService = blogUserService;
        this.commentService = commentService;
    }

    @Secured("ROLE_USER")
    @GetMapping("/comment/{id}")
    public String showComment(@PathVariable Long id, Model model, Principal principal) {

        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<BlogUser> optionalBlogUser = this.blogUserService.findByUsername(authUsername);
        Optional<Post> postOptional = this.postService.getById(id);
        if (postOptional.isPresent() && optionalBlogUser.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(postOptional.get());
            comment.setUser(optionalBlogUser.get());
            model.addAttribute("comment", comment);
            System.err.println("GET comment/{id}: " + comment + "/" + id);
            return "commentForm";
        } else {
            System.err.println("Could not find a post by id: " + id + " or user by logged in username: " + authUsername);
            return "error";
        }
    }

     @Secured("ROLE_USER")
    @PostMapping("/comment")
    public String validateComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, SessionStatus sessionStatus) {
        System.err.println("POST comment: " + comment);
        if (bindingResult.hasErrors()) {
            System.err.println("Comment did not validate");
            return "commentForm";
        } else {
            this.commentService.save(comment);
            System.err.println("SAVE comment: " + comment);
            sessionStatus.setComplete();
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

}
