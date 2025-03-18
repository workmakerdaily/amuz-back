package amuz.todo_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import amuz.todo_back.entity.CalendarEntity;
import jakarta.transaction.Transactional;

@Repository
public interface CalendarRepository  extends JpaRepository<CalendarEntity, Integer> {

    List<CalendarEntity> findAllByUserId(String userId);
    
    @Modifying
    @Transactional
    void deleteByUserIdAndGoalAndCompletedDate(String userId, String goal, String completedDate);

}
