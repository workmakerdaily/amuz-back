package amuz.todo_back.dto.response.todo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import amuz.todo_back.common.object.ToDo;
import amuz.todo_back.dto.response.ResponseCode;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.ResponseMessage;
import amuz.todo_back.entity.ToDoEntity;
import lombok.Getter;

@Getter
public class GetToDoListResponseDto extends ResponseDto {
    
    private List<ToDo> toDoList;

    private GetToDoListResponseDto(List<ToDoEntity> toDoEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.toDoList = ToDo.getList(toDoEntities);
    }

    public static ResponseEntity<GetToDoListResponseDto> success(List<ToDoEntity> toDoEntities) {
        GetToDoListResponseDto responsebody = new GetToDoListResponseDto(toDoEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responsebody);
    }
}
