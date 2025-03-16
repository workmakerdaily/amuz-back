package amuz.todo_back.dto.request.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostToDoRequestDto {
    @NotBlank
    @Size(max = 50)
    private String goal;
}
