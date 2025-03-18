package amuz.todo_back.dto.request.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchToDoRequestDto {
    @NotBlank
    private String goal;
}
