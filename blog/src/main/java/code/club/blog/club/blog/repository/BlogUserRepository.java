package code.club.blog.club.blog.repository;

import code.club.blog.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    // пошук користувача за username
    Optional<BlogUser> findByUsername(String username);
}
// null point exception => NPE
// null - порожній об'єкт

