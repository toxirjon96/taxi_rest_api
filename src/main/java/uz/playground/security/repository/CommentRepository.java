package uz.playground.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.playground.security.entity.Comments;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPostId(Long postId);
}
