
import hospital.dto.UserDTO;
import hospital.entity.User;
import hospital.repository.UserRepository;
import hospital.service.user.UserService;
import hospital.service.user.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShaPasswordEncoder passwordEncoder;

    private User testUser;
    private UserDTO testUserDTO;
    private User createdUser;


    @Before
    public void initialize() {
        userService = new UserServiceImpl(userRepository,new ShaPasswordEncoder());


        testUser=new User("Numele testat","Parola123?","Employee");

        testUserDTO=new UserDTO();
        testUserDTO.password="Parola123?";
        testUserDTO.username="Numele testat";

        when( userRepository.save(any(User.class))).thenReturn(testUser);

        createdUser=userService.create(testUserDTO);

        List<User> allFound=new ArrayList<>();
        allFound.add(createdUser);
        when(userRepository.findAll()).thenReturn(allFound);
    }

    @Test
    public void createTest()
    {
        Assert.assertTrue(createdUser.getUsername().equals(testUser.getUsername()));
        Assert.assertTrue(createdUser.getPassword().equals(testUser.getPassword()));
    }

    @Test
    public void findAllTest()
    {
        Assert.assertTrue(userService.getAll().contains(createdUser));
    }

}
