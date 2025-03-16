package amuz.todo_back.dto.request.todo;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchToDoPriorityRequestDto {
    @NotNull
    private List<Integer> priorityIds;
}
