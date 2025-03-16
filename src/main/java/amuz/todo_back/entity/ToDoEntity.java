package amuz.todo_back.entity;

import amuz.todo_back.dto.request.todo.PostToDoRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "to_do_lists")
@Table(name = "to_do_lists")
public class ToDoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private String goal;
    private Boolean isChecked = false;
    private Integer priority;
    
    public ToDoEntity(PostToDoRequestDto dto) {
        
        this.goal = dto.getGoal();
        
    }
}
