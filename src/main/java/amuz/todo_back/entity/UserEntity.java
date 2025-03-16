package amuz.todo_back.entity;

import amuz.todo_back.dto.request.auth.SignUpRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {
    
    @Id
    private String userId;
    private String userName;
    private String password;

    public UserEntity(SignUpRequestDto dto) {
    this.userId = dto.getUserId();
    this.userName = dto.getUserName();
    this.password = dto.getPassword();
    }

}
