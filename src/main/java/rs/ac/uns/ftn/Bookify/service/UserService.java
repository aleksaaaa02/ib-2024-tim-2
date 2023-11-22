package rs.ac.uns.ftn.Bookify.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.PasswordUpdateDTO;
import rs.ac.uns.ftn.Bookify.dto.UserCredentialsDTO;
import rs.ac.uns.ftn.Bookify.dto.UserDTO;
import rs.ac.uns.ftn.Bookify.dto.UserDetailDTO;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService implements IUserService {
    // @Autowired
    // private UserRepository userRepository;
    @Override
    public Collection<UserDTO> getAll() {
        Collection<UserDTO> users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            users.add(new UserDTO(1231L, "test@example.com", "Pera","Peric", false));
        }
        return users;
    }

    @Override
    public UserDetailDTO get(Long userId) {
        if(userId == 1231L)
            return new UserDetailDTO(1231L, "test@example.com", "Pera","Peric", false, "+381412412", new Address(123L,"Srbija","Novi Sad", "Trg Dositeja Obradovica 6", "2100"));
        return null;
    }

    @Override
    public UserDetailDTO create(UserDetailDTO newUser) {
        newUser.setId(123L);
        return newUser;
    }

    @Override
    public UserDetailDTO update(UserDetailDTO updatedUser) {
        updatedUser.setEmail("someotheradress@gmail.com");
        updatedUser.setFirstName("Da izmenjeno je");
        return updatedUser;
    }


    public boolean changePassword(Long userId, PasswordUpdateDTO newPassword){
        return true;
    }

    @Override
    public boolean resetPassword() {
        // sends email to reset password
        return false;
    }

    @Override
    public boolean activateUser(Long userId) {
        return true;
    }

    @Override
    public boolean login(UserCredentialsDTO userCredentials) {
        return true;
    }

    @Override
    public boolean deleteUser(Long userId) {
        return true;
    }

    @Override
    public boolean blockUser(Long userId) {
        return false;
    }

    @Override
    public Collection<UserDTO> searchUsers(String searchParam) {
        return null;
    }


}
