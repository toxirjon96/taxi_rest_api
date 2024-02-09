package uz.playground.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.playground.security.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long postId);
}
