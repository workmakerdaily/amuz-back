package amuz.todo_back.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.calendar.GetCalendarResponseDto;
import amuz.todo_back.entity.CalendarEntity;
import amuz.todo_back.repository.CalendarRepository;
import amuz.todo_back.service.CalendarService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarServiceImplement implements CalendarService {

    private final CalendarRepository calendarRepository;

    @Override
    public ResponseEntity<? super GetCalendarResponseDto> getCalendarData(String userId) {
        
        List<CalendarEntity> calendarEntities = new ArrayList<>();

        try {

            List<CalendarEntity> isCheckedGoals = calendarRepository.findAllByUserId(userId);

            if(isCheckedGoals.isEmpty()) {
                return ResponseDto.noExistData();
            };

            calendarEntities = isCheckedGoals;

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCalendarResponseDto.success(calendarEntities);
    }
    
}
