package amuz.todo_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import amuz.todo_back.dto.request.todo.PatchToDoIsCheckedRequestDto;
import amuz.todo_back.dto.request.todo.PatchToDoPriorityRequestDto;
import amuz.todo_back.dto.request.todo.PostToDoRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.todo.GetToDoListResponseDto;
import amuz.todo_back.service.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;
    
    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postToDo(
        @RequestBody @Valid PostToDoRequestDto dto,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = toDoService.postToDo(dto, userId);
        return response;
    };

    @GetMapping(value = {"","/"})
    public ResponseEntity<? super GetToDoListResponseDto> getToDoList(
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetToDoListResponseDto> response = toDoService.getToDoList(userId);
        return response;
    }

    @PatchMapping("/is-check/{id}")
    public ResponseEntity<ResponseDto> patchToDoIsChecked(
        @PathVariable("id") Integer id,
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PatchToDoIsCheckedRequestDto request
    ) {
        ResponseEntity<ResponseDto> response = toDoService.patchToDoIsChecked(id, userId, request);
        return response;
    }

    @PatchMapping("/priority")
    public ResponseEntity<ResponseDto> patchToDoPriority(
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PatchToDoPriorityRequestDto request
    ) {
        ResponseEntity<ResponseDto> response = toDoService.patchToDoPriority(userId, request);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteToDo(
        @PathVariable("id") Integer id,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = toDoService.deleteToDo(id, userId);
        return response; 
    }
    
}
