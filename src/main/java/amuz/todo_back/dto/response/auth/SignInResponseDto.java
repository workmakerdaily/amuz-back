package amuz.todo_back.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import amuz.todo_back.dto.response.ResponseCode;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.ResponseMessage;
import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {
    
    private String accessToken;
    private Integer expiration;

    private SignInResponseDto(String accessToken) {

        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.accessToken = accessToken;
        this.expiration = 10 * 60 * 60;
    }

    public static ResponseEntity<SignInResponseDto> success(String accessToken) {
        SignInResponseDto response = new SignInResponseDto(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
