package code.club.blog.club.blog.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment<Date> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    // відношення між сутностями
    // one-to-many, 1:M, (ОДИН ДО БАГАТЬОХ)
    // many-to-one M:1 (БАГАТО ДО ОДНОГО)
    // one-to-one, 1:1 (один до однго)
    // many-to-many M:M

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;


    private BlogUser user;
    // todo: додати юзера

    public BlogUser getUser() {
        return user;
    }

    public void setUser(BlogUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

// table comments
// id | body | creation_date | post_id
// 1 | <text>| 2020-05-13 |    1

// table - posts
// id | title | body | creation_date
// 1  | "news"| <text>|2022-05-13