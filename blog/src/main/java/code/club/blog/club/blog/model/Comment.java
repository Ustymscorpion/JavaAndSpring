package code.club.blog.club.blog.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body" , columnDefinition = "TEXT")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    //відношення між сутностями
    //one-to-many, 1:M
    //many-to-one, M:1
    //one-to-one, 1:1
    //many-to-many, M:M

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
}

// table comments
// id | body | creation_date | post_id
// 1 | <text>| 2020-05-13 |    1

// table - posts
// id | title | body | creation_date
// 1  | "news"| <text>|2022-05-13