package amuz.todo_back.service.Implement;

import org.springframework.stereotype.Service;

import amuz.todo_back.repository.UserRepository;
import amuz.todo_back.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    
    private final UserRepository userRepository;

}
