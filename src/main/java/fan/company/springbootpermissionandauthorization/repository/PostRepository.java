package fan.company.springbootpermissionandauthorization.repository;

import fan.company.springbootpermissionandauthorization.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitleOrUrl(String title, String url);

}
