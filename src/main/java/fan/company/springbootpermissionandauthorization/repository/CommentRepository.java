package fan.company.springbootpermissionandauthorization.repository;

import fan.company.springbootpermissionandauthorization.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {



}
