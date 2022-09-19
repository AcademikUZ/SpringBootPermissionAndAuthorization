package fan.company.springbootpermissionandauthorization.controller;

import fan.company.springbootpermissionandauthorization.annotation.HuquqniTekshirish;
import fan.company.springbootpermissionandauthorization.entity.Lavozim;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.RoleDto;
import fan.company.springbootpermissionandauthorization.service.LavozimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class LavozimController {

    @Autowired
    LavozimService service;

    @HuquqniTekshirish(huquq = "ADD_LAVOZIMLAR")
    //@PreAuthorize(value = "hasAuthority('ADD_LAVOZIM')")
    @PostMapping
    public HttpEntity<?> addRole(@Valid @RequestBody RoleDto dto) {
        ApiResult apiResult = service.addRole(dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_LAVOZIM')")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody RoleDto dto) {
        ApiResult apiResult = service.editRole(id, dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_LAVOZIM')")
    @GetMapping
    public HttpEntity<?> getAll(@RequestParam Integer page) {
        Page<Lavozim> all = service.getAll(page);
        return ResponseEntity.status(!all.isEmpty()? HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_LAVOZIM')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        Lavozim one = service.getOne(id);
        return ResponseEntity.status(one != null? HttpStatus.OK:HttpStatus.CONFLICT).body(one);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_LAVOZIM')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResult apiResult = service.delete(id);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiResult);
    }

}
