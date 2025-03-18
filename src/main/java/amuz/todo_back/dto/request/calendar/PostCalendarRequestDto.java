package amuz.todo_back.dto.request.calendar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostCalendarRequestDto {
    
    @NotBlank
    @Size(max = 20)
    private String userId;
    @NotBlank
    private String goal;
}
