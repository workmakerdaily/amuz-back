package amuz.todo_back.common.object;

import java.util.ArrayList;
import java.util.List;

import amuz.todo_back.entity.ToDoEntity;
import lombok.Getter;

@Getter
public class ToDo {

    private Integer id;
    private String userId;
    private String goal;
    private Boolean isChecked;
    private Integer priority;
    private String checkedDate;

    public ToDo(ToDoEntity toDoEntity) {

        this.id = toDoEntity.getId();
        this.userId = toDoEntity.getUserId();
        this.goal = toDoEntity.getGoal();
        this.isChecked = toDoEntity.getIsChecked();
        this.priority = toDoEntity.getPriority();
        this.checkedDate = toDoEntity.getCheckedDate();

    }

    public static List<ToDo> getList(List<ToDoEntity> toDoEntities) {

        List<ToDo> toDos = new ArrayList<>();

        for (ToDoEntity toDoEntity : toDoEntities) {
            ToDo toDo = new ToDo(toDoEntity);
            toDos.add(toDo);
        }
        return toDos;
    }
}
