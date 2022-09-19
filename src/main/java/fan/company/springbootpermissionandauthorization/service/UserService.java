package fan.company.springbootpermissionandauthorization.service;

import fan.company.springbootpermissionandauthorization.entity.Lavozim;
import fan.company.springbootpermissionandauthorization.entity.User;
import fan.company.springbootpermissionandauthorization.exceptions.ResourceNotFoundException;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.RoleDto;
import fan.company.springbootpermissionandauthorization.payload.UserDto;
import fan.company.springbootpermissionandauthorization.repository.LavozimRepository;
import fan.company.springbootpermissionandauthorization.repository.UserRepository;
import fan.company.springbootpermissionandauthorization.security.tokenGenerator.JwtProvider;
import fan.company.springbootpermissionandauthorization.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LavozimRepository lavozimRepository;

    public ApiResult addUser(UserDto dto) {

        if (repository.findByUsername(dto.getUsername()).isPresent())
            return new ApiResult("Bunday username mavjud!", false);
        Optional<Lavozim> optionalLavozim = lavozimRepository.findById(dto.getLavozimId());

        if (optionalLavozim.isEmpty())
            return new ApiResult("Bunday lavozim mavjud emas!", false);

        User user = new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                optionalLavozim.get()
        );

        repository.save(user);

        return new ApiResult("Muvoffaqiyatli ro'yxatdan o'tkazildi!", true);
    }

    public ApiResult edit(Long id, UserDto dto) {

        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent())
            return new ApiResult("Bunday username mavjud!", false);

        Optional<Lavozim> optionalLavozim = lavozimRepository.findById(dto.getLavozimId());
        if (optionalLavozim.isEmpty())
            return new ApiResult("Bunday lavozim mavjud emas!", false);

        User user = new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                optionalLavozim.get()
        );

        user.setId(optionalUser.get().getId());
        repository.save(user);
        return new ApiResult("Muvoffaqiyatli tahrirlandi!", true);
    }

    public Page<User> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public User getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {

        try {
            boolean existsById = repository.existsById(id);
            if (existsById) {
                repository.deleteById(id);
                return new ApiResult("O'chirildi", true);
            }
            return new ApiResult("Bunday User mavjud emas", false);
        } catch (Exception e) {
            return new ApiResult("Xatolik", false);
        }
    }

}
