package hospital.service.user;

import hospital.dto.UserDTO;
import hospital.entity.User;
import hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ShaPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ShaPasswordEncoder shaPasswordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=shaPasswordEncoder;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO userDTO) {
        User user=new User(userDTO.username,passwordEncoder.encodePassword(userDTO.password,null),userDTO.role);
        return userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        userRepository.delete(username);
    }

    @Override
    public User update(UserDTO userDTO) {
        User user=userRepository.findOne(userDTO.username);
        user.setPassword(passwordEncoder.encodePassword(userDTO.password,null));
        return userRepository.save(user);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findOne(username);
    }

    @Override
    public List<User> findDoctors() {
        return userRepository.findByRole("DOCTOR");
    }

}
