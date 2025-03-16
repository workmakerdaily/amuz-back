package amuz.todo_back.service.Implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import amuz.todo_back.dto.request.todo.PatchToDoIsCheckedRequestDto;
import amuz.todo_back.dto.request.todo.PatchToDoPriorityRequestDto;
import amuz.todo_back.dto.request.todo.PostToDoRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.todo.GetToDoListResponseDto;
import amuz.todo_back.entity.ToDoEntity;
import amuz.todo_back.entity.UserEntity;
import amuz.todo_back.repository.ToDoRepository;
import amuz.todo_back.repository.UserRepository;
import amuz.todo_back.service.ToDoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoServiceImplement implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> postToDo(PostToDoRequestDto dto, String userId) {

        try {

        ToDoEntity toDoEntity = new ToDoEntity(dto);

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) 
            return ResponseDto.noExistUserId();

        Integer maxPriority = toDoRepository.findMaxPriorityByUserId(userId);
        if (maxPriority == null) maxPriority = 0;

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

        try {

            toDoEntities = toDoRepository.findByUserIdOrderByPriorityAsc(userId);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return GetToDoListResponseDto.success(toDoEntities);
    }

    @Override
    public ResponseEntity<ResponseDto> patchToDoIsChecked(Integer id, String userId, PatchToDoIsCheckedRequestDto dto) {
        
        try {

            Optional<ToDoEntity> optionalToDo = toDoRepository.findById(id);
            UserEntity userEntity = userRepository.findByUserId(userId);

            if (optionalToDo == null) {
                return ResponseDto.noExistToDo();
            }

            if(!userEntity.getUserId().equals(userId)) {
                return ResponseDto.noPermission();
            }

            ToDoEntity toDoEntity = optionalToDo.get();
            toDoEntity.setIsChecked(dto.getIsChecked());
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
    public ResponseEntity<ResponseDto> patchToDoPriority(String userId, PatchToDoPriorityRequestDto dto) {
    
        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) {
                return ResponseDto.noExistUserId();
            }

            if (dto == null || dto.getPriorityIds() == null || dto.getPriorityIds().isEmpty()) {
                return ResponseDto.databaseError();
            }

            List<Integer> orderedIds = dto.getPriorityIds();
            List<ToDoEntity> toDoEntities = toDoRepository.findAllById(orderedIds);

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
    
}
