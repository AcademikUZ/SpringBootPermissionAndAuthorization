package fan.company.springbootpermissionandauthorization.controller;

import fan.company.springbootpermissionandauthorization.entity.User;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.UserDto;
import fan.company.springbootpermissionandauthorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping("/addUser")
    public HttpEntity<?> register(@Valid @RequestBody UserDto dto){
        ApiResult apiResult = service.addUser(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_USER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody UserDto dto) {
        ApiResult apiResult = service.edit(id, dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_USER')")
    @GetMapping
    public HttpEntity<?> getAll(@RequestParam Integer page) {
        Page<User> all = service.getAll(page);
        return ResponseEntity.status(!all.isEmpty()? HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_USER')")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        User one = service.getOne(id);
        return ResponseEntity.status(one != null? HttpStatus.OK:HttpStatus.CONFLICT).body(one);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_USER')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResult apiResult = service.delete(id);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiResult);
    }



}
