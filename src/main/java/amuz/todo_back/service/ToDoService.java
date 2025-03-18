package amuz.todo_back.service;

import org.springframework.http.ResponseEntity;

import amuz.todo_back.dto.request.todo.PatchToDoIsCheckedRequestDto;
import amuz.todo_back.dto.request.todo.PatchToDoPriorityRequestDto;
import amuz.todo_back.dto.request.todo.PatchToDoRequestDto;
import amuz.todo_back.dto.request.todo.PostToDoRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.todo.GetToDoListResponseDto;

public interface ToDoService {
    
    ResponseEntity<ResponseDto> postToDo(PostToDoRequestDto dto,String userId);
    
    ResponseEntity<? super GetToDoListResponseDto> getToDoList(String userId);

    ResponseEntity<ResponseDto> patchToDo(Integer id, String userId, PatchToDoRequestDto dto);

    ResponseEntity<ResponseDto> patchToDoIsChecked(Integer id, String userId, PatchToDoIsCheckedRequestDto dto);

    ResponseEntity<ResponseDto> patchToDoPriority(String userId, PatchToDoPriorityRequestDto dto);

    ResponseEntity<ResponseDto> deleteToDo(Integer id, String userId);

    ResponseEntity<ResponseDto> resetIsChecked();
}
