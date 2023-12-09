package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.Collection;

public interface IUserService {
    public Collection<UserDTO> getAll();
    public UserDetailDTO find(Long userId);
    public Long create(UserRegisteredDTO newUser);
    public UserDetailDTO update(UserDetailDTO updatedUser);
    public boolean changePassword(Long userId, String newPassword);
    public boolean resetPassword();
    public boolean activateUser(Long userId);
    public boolean login(UserCredentialsDTO userCredentials);
    public boolean deleteUser(Long userId);
    public boolean blockUser(Long userId);
    public Collection<UserDTO> searchUsers(String searchParam);
    public Long updateImage(byte[] bytes, String imageName, Long userId) throws Exception;
    public FileSystemResource getImage(Long imageId);
    public OwnerDTO findbyAccommodationId(Long id);
}
