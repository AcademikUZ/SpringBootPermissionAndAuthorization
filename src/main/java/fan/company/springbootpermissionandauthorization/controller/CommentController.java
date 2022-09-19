package fan.company.springbootpermissionandauthorization.controller;

import fan.company.springbootpermissionandauthorization.entity.Comment;
import fan.company.springbootpermissionandauthorization.payload.ApiResult;
import fan.company.springbootpermissionandauthorization.payload.CommentDto;
import fan.company.springbootpermissionandauthorization.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService service;

    @PreAuthorize(value = "hasAuthority('ADD_COMMENT')")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody CommentDto dto) {
        ApiResult apiResult = service.add(dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_COMMENT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody CommentDto dto) {
        ApiResult apiResult = service.edit(id, dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    @GetMapping
    public HttpEntity<?> getAll(@RequestParam Integer page) {
        Page<Comment> all = service.getAll(page);
        return ResponseEntity.status(!all.isEmpty()? HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        Comment one = service.getOne(id);
        return ResponseEntity.status(one != null? HttpStatus.OK:HttpStatus.CONFLICT).body(one);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_COMMENT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResult apiResult = service.delete(id);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiResult);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_MY_COMMENT')")
    @DeleteMapping("/deletemycomment/{id}")
    public HttpEntity<?> deleteMyComment(@PathVariable Long id) {
        ApiResult apiResult = service.deleteMyComment(id);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiResult);
    }

}
