package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.Collection;
import java.util.List;

public interface IUserService {
    public List<User> getAll();
    public User get(String email);
    public User get(Long userId);
    public String getRole(User user);
    public User create(UserRegisteredDTO newUser);
    public User update(UserDetailDTO updatedUser);
    public boolean changePassword(Long userId, String newPassword);
    public String resetPassword(String email);
    public boolean activateUser(String uuid);
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
    public void checkInactiveUsers();
    public List<AccommodationRequestDTO> findAccommodationRequests();
    public UserReservationDTO getOwnerForReservation(Long accommodationId);
    public UserReservationDTO getGuestForReservation(Long accommodationId);
    public void addToFavorites(Long guestId, Long accommodationId);
    public boolean checkIfInFavorites(Long guestId, Long accommodationId);
}
