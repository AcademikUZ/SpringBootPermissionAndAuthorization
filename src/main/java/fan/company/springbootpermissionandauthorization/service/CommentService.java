package fan.company.springbootpermissionandauthorization.service;

import fan.company.springbootpermissionandauthorization.entity.Comment;
import fan.company.springbootpermissionandauthorization.entity.Post;
import fan.company.springbootpermissionandauthorization.entity.User;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.CommentDto;
import fan.company.springbootpermissionandauthorization.repository.CommentRepository;
import fan.company.springbootpermissionandauthorization.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository repository;
    @Autowired
    PostRepository postRepository;

    public ApiResult add(CommentDto dto) {

        Optional<Post> optionalPost = postRepository.findById(dto.getPostId());
        if(optionalPost.isEmpty())
            return new ApiResult("Bunday Comment mavjud emas!", false);

        Comment comment = new Comment(
                dto.getText(),
                optionalPost.get()
        );

        repository.save(comment);

        return new ApiResult("Muvoffaqiyatli saqlandi!", true);
    }

    public ApiResult edit(Long id, CommentDto dto) {

        Optional<Comment> optionalComment = repository.findById(id);

        if (optionalComment.isEmpty())
            return new ApiResult("Bunday Comment mavjud emas!", false);

        Optional<Post> optionalPost = postRepository.findById(dto.getPostId());
        if(optionalPost.isEmpty())
            return new ApiResult("Bunday Comment mavjud emas!", false);

        Comment comment = new Comment(
                dto.getText(),
                optionalPost.get()
        );

        comment.setId(optionalComment.get().getId());

        repository.save(comment);

        return new ApiResult("Muvoffaqiyatli tahrirlandi!", true);
    }

    public Page<Comment> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Comment getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {

        try {
            boolean existsById = repository.existsById(id);
            if (existsById) {
                repository.deleteById(id);
                return new ApiResult("O'chirildi", true);
            }
            return new ApiResult("Bunday Comment mavjud emas", false);
        } catch (Exception e) {
            return new ApiResult("Xatolik", false);
        }
    }

    public ApiResult deleteMyComment(Long id) {

        try {
            Optional<Comment> optionalComment = repository.findById(id);
            if (optionalComment.isEmpty())
                return new ApiResult("Bunday Comment mavjud emas!", false);

            try {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(user.getId() != optionalComment.get().getCreatedBy())
                    return new ApiResult("Sizda bunday huquq yo'q!", false);
            } catch (Exception e){
                return new ApiResult("Xato!", false);
            }

            repository.deleteById(id);
                return new ApiResult("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResult("Xatolik", false);
        }

    }
}
