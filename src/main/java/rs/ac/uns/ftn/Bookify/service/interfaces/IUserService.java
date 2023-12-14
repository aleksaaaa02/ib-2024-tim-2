package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.Collection;

public interface IUserService {
    public Collection<User> getAll();
    public User get(String email);
    public User get(Long userId);
    public String getRole(User user);
    public User create(UserRegisteredDTO newUser);
    public User update(UserDetailDTO updatedUser);
    public boolean changePassword(Long userId, String newPassword);
    public boolean resetPassword();
    public boolean activateUser(Long userId);
    public boolean isLoginAvailable(Long userId);
    public boolean delete(Long userId);
    public boolean block(Long userId);
    public Collection<User> searchUsers(String searchParam);
    public Long updateImage(byte[] bytes, String imageName, Long userId) throws Exception;
    public FileSystemResource getImage(Long imageId);
    public OwnerDTO findbyAccommodationId(Long id);
    public Float getAvgRating(Long id);
    public void saveOwnerAccommodation(Accommodation accommodation, Long ownerId);
    public OwnerDTO setOwnerForAccommodation(Long id);
}
