package amuz.todo_back.dto.request.todo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchToDoIsCheckedRequestDto {
    
    @NotNull
    private Boolean isChecked;
}
