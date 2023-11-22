package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.PasswordUpdateDTO;
import rs.ac.uns.ftn.Bookify.dto.UserCredentialsDTO;
import rs.ac.uns.ftn.Bookify.dto.UserDTO;
import rs.ac.uns.ftn.Bookify.dto.UserDetailDTO;

import java.util.Collection;

public interface IUserService {
    public Collection<UserDTO> getAll();
    public UserDetailDTO get(Long userId);
    public UserDetailDTO create(UserDetailDTO newUser);
    public UserDetailDTO update(UserDetailDTO updatedUser);
    public boolean changePassword(Long userId, PasswordUpdateDTO newPassword);
    public boolean resetPassword();
    public boolean activateUser(Long userId);
    public boolean login(UserCredentialsDTO userCredentials);
    public boolean deleteUser(Long userId);
    public boolean blockUser(Long userId);
    public Collection<UserDTO> searchUsers(String searchParam);
}
