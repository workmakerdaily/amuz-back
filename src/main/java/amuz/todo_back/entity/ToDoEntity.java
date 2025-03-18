package amuz.todo_back.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import amuz.todo_back.dto.request.todo.PostToDoRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String checkedDate;
    
    public ToDoEntity(PostToDoRequestDto dto) {

        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        
        this.goal = dto.getGoal();
        this.checkedDate = simpleDateFormat.format(now);
        
    }
}
