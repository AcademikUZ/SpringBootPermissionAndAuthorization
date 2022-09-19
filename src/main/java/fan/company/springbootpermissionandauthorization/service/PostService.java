package fan.company.springbootpermissionandauthorization.service;

import fan.company.springbootpermissionandauthorization.entity.Lavozim;
import fan.company.springbootpermissionandauthorization.entity.Post;
import fan.company.springbootpermissionandauthorization.entity.User;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.PostDto;
import fan.company.springbootpermissionandauthorization.repository.PostRepository;
import fan.company.springbootpermissionandauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    public ApiResult add(PostDto dto) {

        if (repository.findByTitleOrUrl(dto.getTitle(), dto.getUrl()).isPresent())
            return new ApiResult("Bunday Post mavjud!", false);

        Post post = new Post(
                dto.getTitle(),
                dto.getText(),
                dto.getUrl()
        );

        repository.save(post);

        return new ApiResult("Muvoffaqiyatli saqlandi!", true);
    }

    public ApiResult edit(Long id, PostDto dto) {

        Optional<Post> optionalPost = repository.findById(id);

        if (optionalPost.isEmpty())
            return new ApiResult("Bunday Post mavjud emas!", false);

        Post post = new Post(
                dto.getTitle(),
                dto.getText(),
                dto.getUrl()
        );

        post.setId(optionalPost.get().getId());
        repository.save(post);

        return new ApiResult("Muvoffaqiyatli tahrirlandi!", true);
    }

    public Page<Post> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Post getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {

        try {
            boolean existsById = repository.existsById(id);
            if (existsById) {
                repository.deleteById(id);
                return new ApiResult("O'chirildi", true);
            }
            return new ApiResult("Bunday Post mavjud emas", false);
        } catch (Exception e) {
            return new ApiResult("Xatolik", false);
        }
    }

}
