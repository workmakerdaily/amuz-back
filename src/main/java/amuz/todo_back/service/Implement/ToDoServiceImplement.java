package amuz.todo_back.service.Implement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import amuz.todo_back.dto.request.todo.PatchToDoIsCheckedRequestDto;
import amuz.todo_back.dto.request.todo.PatchToDoPriorityRequestDto;
import amuz.todo_back.dto.request.todo.PatchToDoRequestDto;
import amuz.todo_back.dto.request.todo.PostToDoRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.todo.GetToDoListResponseDto;
import amuz.todo_back.entity.CalendarEntity;
import amuz.todo_back.entity.ToDoEntity;
import amuz.todo_back.entity.UserEntity;
import amuz.todo_back.repository.CalendarRepository;
import amuz.todo_back.repository.ToDoRepository;
import amuz.todo_back.repository.UserRepository;
import amuz.todo_back.service.ToDoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoServiceImplement implements ToDoService {

    private final CalendarRepository calendarRepository;

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> postToDo(PostToDoRequestDto dto, String userId) {

        try {

        ToDoEntity toDoEntity = new ToDoEntity(dto);

        if (dto.getGoal() == null) {
            return ResponseDto.validationFail();
        }

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) 
            return ResponseDto.noExistUserId();

        Integer maxPriority = toDoRepository.findMaxPriorityByUserId(userId);

        toDoEntity.setUserId(userId);
        toDoEntity.setPriority(maxPriority + 1);
        toDoRepository.save(toDoEntity);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetToDoListResponseDto> getToDoList(String userId) {
        
        List<ToDoEntity> toDoEntities = new ArrayList<>();
        
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) 
            return ResponseDto.noExistUserId();

        try {

            toDoEntities = toDoRepository.findByUserIdOrderByPriority(userId);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return GetToDoListResponseDto.success(toDoEntities);
    }

    @Override
    public ResponseEntity<ResponseDto> patchToDo(Integer id, String userId, PatchToDoRequestDto dto) {
        try {

            Optional<ToDoEntity> optionalToDo = toDoRepository.findById(id);
            UserEntity userEntity = userRepository.findByUserId(userId);

            if (optionalToDo.isEmpty()) {
                return ResponseDto.noExistToDo();
            }

            if (userEntity == null) {
                return ResponseDto.noExistUserId();
            }

            if (!userEntity.getUserId().equals(userId)) {
                return ResponseDto.noPermission();
            }
            ToDoEntity toDoEntity = optionalToDo.get();
            String patchToDo = dto.getGoal();

            Integer PriorityDesc = toDoEntity.getPriority();
            
            toDoEntity.setGoal(patchToDo);
            toDoEntity.setPriority(PriorityDesc);

            toDoRepository.save(toDoEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> patchToDoIsChecked(Integer id, String userId, PatchToDoIsCheckedRequestDto dto) {
        
        try {

            Optional<ToDoEntity> optionalToDo = toDoRepository.findById(id);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) 
                return ResponseDto.noExistUserId();

            if (optionalToDo.isEmpty()) {
                return ResponseDto.noExistToDo();
            }

            if(!userEntity.getUserId().equals(userId)) {
                return ResponseDto.noPermission();
            }

            ToDoEntity toDoEntity = optionalToDo.get();
            boolean isChecked = dto.getIsChecked();

            toDoEntity.setIsChecked(isChecked);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (isChecked) {
                
                toDoEntity.setCheckedDate(simpleDateFormat.format(new Date()));

                CalendarEntity calendarEntity = new CalendarEntity();
                calendarEntity.setUserId(toDoEntity.getUserId());
                calendarEntity.setGoal(toDoEntity.getGoal());
                calendarEntity.setCompletedDate(toDoEntity.getCheckedDate());

                calendarRepository.save(calendarEntity);
            } else {
                String completedDate = toDoEntity.getCheckedDate();
                toDoEntity.setCheckedDate(null);

                calendarRepository.deleteByUserIdAndGoalAndCompletedDate(
                    userId, toDoEntity.getGoal(), completedDate
                );
            }
            toDoRepository.save(toDoEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteToDo(Integer id, String userId) {

        try{
            Optional<ToDoEntity> optionalToDo = toDoRepository.findById(id);
            UserEntity userEntity = userRepository.findByUserId(userId);

            if (optionalToDo == null) {
                return ResponseDto.noExistToDo();
            }

            if (userEntity == null) {
                return ResponseDto.noExistUserId();
            }

            if(!userEntity.getUserId().equals(userId)) {
                return ResponseDto.noPermission();
            }
            
            toDoRepository.delete(optionalToDo.get());
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto> patchToDoPriority(String userId, PatchToDoPriorityRequestDto dto) {
    
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);

            if (userEntity == null) {
                return ResponseDto.noExistUserId();
            }

            if (dto == null || dto.getPriorityIds() == null || dto.getPriorityIds().isEmpty()) {
                return ResponseDto.databaseError();
            }

            if(!userEntity.getUserId().equals(userId)) {
                return ResponseDto.noPermission();
            }

            List<Integer> orderedIds = dto.getPriorityIds();
            List<ToDoEntity> toDoEntities = toDoRepository.findAllById(orderedIds);

            if (toDoEntities.size() != orderedIds.size()) {
                return ResponseDto.noExistToDo();
            }

            for (int i = 0; i < orderedIds.size(); i++) {
                Integer id = orderedIds.get(i);
                for (ToDoEntity toDoEntity : toDoEntities) {
                    if (toDoEntity.getId().equals(id)) {
                        toDoEntity.setPriority(i + 1);
                        break;
                    }
                }
            }
            toDoRepository.saveAll(toDoEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public ResponseEntity<ResponseDto> resetIsChecked() {
        try {
            toDoRepository.resetIsChecked();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

}
