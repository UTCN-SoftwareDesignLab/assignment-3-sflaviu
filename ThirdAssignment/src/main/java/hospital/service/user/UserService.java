package hospital.service.user;

import hospital.dto.UserDTO;
import hospital.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User create(UserDTO userDTO);
    void delete(String username);
    User update(UserDTO userDTO);

    User findByUsername(String username);
    List<User> findDoctors();

}
