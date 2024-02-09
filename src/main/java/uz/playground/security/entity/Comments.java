package uz.playground.security.entity;
import uz.playground.security.constant.TableName;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = TableName.COMMENTS)
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_comments_sequnce_db")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "description", length = 600)
    private String description;

    @Column(name = "comment_time")
    private LocalDateTime commentTime;

    public Comments() {
    }

    public Comments(Long postId, String description, LocalDateTime commentTime) {
        this.postId = postId;
        this.description = description;
        this.commentTime = commentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", postId=" + postId +
                ", description='" + description + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
