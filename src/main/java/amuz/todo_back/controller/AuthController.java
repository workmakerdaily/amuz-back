package amuz.todo_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import amuz.todo_back.dto.request.auth.IdCheckRequestDto;
import amuz.todo_back.dto.request.auth.SignInRequestDto;
import amuz.todo_back.dto.request.auth.SignUpRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.auth.SignInResponseDto;
import amuz.todo_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/id-check")
    public ResponseEntity<ResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto request) {
        ResponseEntity<ResponseDto> response = authService.idCheck(request);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> SignUp(@RequestBody @Valid SignUpRequestDto request) {
        ResponseEntity<ResponseDto> response = authService.signUp(request);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
}
