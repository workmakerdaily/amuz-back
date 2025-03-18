package amuz.todo_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amuz.todo_back.entity.ToDoEntity;
import jakarta.transaction.Transactional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Integer> {
    
    List<ToDoEntity> findByUserIdOrderByPriorityAsc(String userId);

    @Query("SELECT COALESCE(MAX(t.priority), 0) FROM to_do_lists t WHERE t.userId = :userId")
    int findMaxPriorityByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("UPDATE to_do_lists t SET t.isChecked = false WHERE t.isChecked = true")
    void resetIsChecked();
}

