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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.swing.text.html.Option;
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
        this.blogUserService = blogUserService;
        this.commentService = commentService;
        this.postService = postService;
    }

    @Secured("ROLE_USER")
    @GetMapping("/comment/{id}")
    public String showComment(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<BlogUser> optionalBlogUser = blogUserService.findByUsername(authUsername);
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent() && optionalBlogUser.isPresent()) {
            Comment comment = new Comment<>();
            comment.setPost(optionalPost.get());
            comment.setUser(optionalBlogUser.get());
            model.addAttribute("comment", comment);
            return "commentForm";
        } else {
            System.err.println("Could not find a post or user");
            return "error";
        }
    }

}
