package fan.company.springbootpermissionandauthorization.service;

import fan.company.springbootpermissionandauthorization.entity.Lavozim;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.RoleDto;
import fan.company.springbootpermissionandauthorization.repository.LavozimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LavozimService {

    @Autowired
    LavozimRepository repository;

    public ApiResult addRole(RoleDto dto) {

        if (repository.findByName(dto.getName()).isPresent())
            return new ApiResult("Bunday lavozim mavjud!", false);
        Lavozim lavozim = new Lavozim(
                dto.getName(), dto.getHuquqList(), dto.getDescription()
        );

        repository.save(lavozim);

        return new ApiResult("Lavozim saqlandi!", true);
    }

    public ApiResult editRole(Long id, RoleDto dto) {

        Optional<Lavozim> optionalLavozim = repository.findById(id);

        if (optionalLavozim.isEmpty())
            return new ApiResult("Bunday lavozim mavjud emas!", false);

        Lavozim lavozim = optionalLavozim.get();
        lavozim.setName(dto.getName());
        lavozim.setDescription(dto.getDescription());
        lavozim.setHuquqList(dto.getHuquqList());

        repository.save(lavozim);

        return new ApiResult("Lavozim saqlandi!", true);
    }

    public Page<Lavozim> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Lavozim getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {

        try {
            boolean existsById = repository.existsById(id);
            if (existsById) {
                repository.deleteById(id);
                return new ApiResult("O'chirildi", true);
            }
            return new ApiResult("Bunday Lavozim mavjud emas", false);
        } catch (Exception e) {
            return new ApiResult("Xatolik", false);
        }
    }



}
