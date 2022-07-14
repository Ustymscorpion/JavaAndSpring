package code.club.blog.club.blog.model;

import net.bytebuddy.utility.dispatcher.JavaDispatcher;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class BlogUser {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_USERNAME_LENGHT = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "pasword")
    private String pasword;

    @Column()
}
