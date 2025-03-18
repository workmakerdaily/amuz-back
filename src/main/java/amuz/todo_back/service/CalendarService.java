package amuz.todo_back.service;

import org.springframework.http.ResponseEntity;

import amuz.todo_back.dto.response.calendar.GetCalendarResponseDto;

public interface CalendarService {
    ResponseEntity<? super GetCalendarResponseDto> getCalendarData(String userId);
}
