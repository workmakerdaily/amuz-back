package amuz.todo_back.common.object;

import java.util.ArrayList;
import java.util.List;

import amuz.todo_back.entity.CalendarEntity;
import lombok.Getter;

@Getter
public class Calendar {
    private Integer id;
    private String userId;
    private String goal;
    private String completedDate;

    public Calendar(CalendarEntity calendarEntity) {

        this.id = calendarEntity.getId();
        this.userId = calendarEntity.getUserId();
        this.goal = calendarEntity.getGoal();
        this.completedDate = calendarEntity.getCompletedDate();
    }

    public static List<Calendar> getCalendar(List<CalendarEntity> calendarEntities) {

        List<Calendar> calendars = new ArrayList<>();

        for (CalendarEntity calendarEntity : calendarEntities) {
            Calendar calendar = new Calendar(calendarEntity);
            calendars.add(calendar);
        }
        return calendars;
    }
}
