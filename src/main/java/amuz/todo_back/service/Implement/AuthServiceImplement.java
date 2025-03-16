package amuz.todo_back.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import amuz.todo_back.dto.request.auth.IdCheckRequestDto;
import amuz.todo_back.dto.request.auth.SignInRequestDto;
import amuz.todo_back.dto.request.auth.SignUpRequestDto;
import amuz.todo_back.dto.response.ResponseDto;
import amuz.todo_back.dto.response.auth.SignInResponseDto;
import amuz.todo_back.entity.UserEntity;
import amuz.todo_back.providers.JwtProvider;
import amuz.todo_back.repository.UserRepository;
import amuz.todo_back.service.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {
        
        String userId = dto.getUserId();

        try {

            boolean isExisted = userRepository.existsById(userId);
            if (isExisted)
                return ResponseDto.duplicatedUserId();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {

        String userId = dto.getUserId();
        String password = dto.getPassword();

        try {

            boolean isExisted = userRepository.existsById(userId);
            if (isExisted) return ResponseDto.duplicatedUserId();

            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String userId = dto.getUserId();
        String password = dto.getPassword();

        String accessToken = null;

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) 
                return ResponseDto.signInFail();

            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched)
                return ResponseDto.signInFail();
    
            accessToken = jwtProvider.create(userId);
            if (accessToken == null)
                return ResponseDto.tokenCreateFail();

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return SignInResponseDto.success(accessToken);
    }
    
}
