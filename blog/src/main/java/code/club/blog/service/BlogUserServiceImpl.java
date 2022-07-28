package code.club.blog.service;

import code.club.blog.model.BlogUser;
import code.club.blog.repository.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

// impl => implementation - реалізація
@Service
public class BlogUserServiceImpl implements BlogUserService {
// dependency injection - вставка залежностей
    private final BlogUserRepository blogUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public BlogUserServiceImpl(
            BlogUserRepository blogUserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.blogUserRepository = blogUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<BlogUser> findByUsername(String username) {
        return blogUserRepository.findByUsername(username);
    }

    @Override
    public BlogUser saveNewBlogUser(BlogUser blogUser) throws RoleNotFoundException {
        blogUser.setPassword(this.bCryptPasswordEncoder.encode(blogUser.getPassword()));
        blogUser.setEnabled(true);
        return this.blogUserRepository.saveAndFlush(blogUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BlogUser> blogUser = blogUserRepository.findByUsername(username);
        if (blogUser.isPresent()) {
            return blogUser.get();
        } else {
            throw new UsernameNotFoundException("No user found for username " + username);
        }
    }
}
