package amuz.todo_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import amuz.todo_back.dto.response.calendar.GetCalendarResponseDto;
import amuz.todo_back.service.CalendarService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping(value = {"","/"})
    public ResponseEntity<? super GetCalendarResponseDto> getCalendarData(
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetCalendarResponseDto> response = calendarService.getCalendarData(userId);
        return response;
    }
}
