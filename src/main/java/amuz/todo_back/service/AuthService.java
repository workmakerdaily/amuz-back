package amuz.todo_back.service;

import org.springframework.http.ResponseEntity;

import amuz.todo_back.dto.request.auth.IdCheckRequestDto;
import amuz.todo_back.dto.request.auth.SignInRequestDto;
import amuz.todo_back.dto.request.auth.SignUpRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.auth.SignInResponseDto;

public interface AuthService {
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}