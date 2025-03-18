package amuz.todo_back.dto.response.calendar;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import amuz.todo_back.common.object.Calendar;
import amuz.todo_back.common.object.ToDo;
import amuz.todo_back.dto.response.ResponseCode;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.ResponseMessage;
import amuz.todo_back.dto.response.todo.GetToDoListResponseDto;
import amuz.todo_back.entity.CalendarEntity;
import amuz.todo_back.entity.ToDoEntity;
import lombok.Getter;

@Getter
public class GetCalendarResponseDto extends ResponseDto {

    private List<Calendar> calendar;

    private GetCalendarResponseDto(List<CalendarEntity> calendarEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.calendar = Calendar.getCalendar(calendarEntities);
    }

    public static ResponseEntity<GetCalendarResponseDto> success(List<CalendarEntity> calendarEntities) {
        GetCalendarResponseDto responsebody = new GetCalendarResponseDto(calendarEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responsebody);
    }
    
}
