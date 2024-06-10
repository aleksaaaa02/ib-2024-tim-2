package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.core.io.FileSystemResource;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.Collection;
import java.util.List;

public interface IUserService {
    public List<User> getAll();
    public User get(String email);
    public User getByUID(String userId);
    public String getRole(User user);
    public User create(UserRegisteredDTO newUser);
    public User update(UserDetailDTO updatedUser);
    public boolean changePassword(Long userId, String newPassword);
    public String resetPassword(String email);
    public UserDTO block(String userId);
    public UserDTO unblock(String userId);
    public Collection<User> searchUsers(String searchParam);
    public Long updateImage(byte[] bytes, String imageName, Long userId) throws Exception;
    public FileSystemResource getImage(Long imageId);
    public OwnerDTO findbyAccommodationId(Long id);
    public Float getAvgRating(String id);
    public void saveOwnerAccommodation(Accommodation accommodation, String ownerId);
    public OwnerDTO setOwnerForAccommodation(Long id);
    public void checkInactiveUsers();
    public List<AccommodationRequestDTO> findAccommodationRequests();
    public Owner getOwner(String ownerId);
    public Guest getGuest(String guestId);
    public void saveOwner(Owner owner);
    public UserReservationDTO getOwnerForReservation(Long accommodationId);
    public UserReservationDTO getGuestForReservation(Long accommodationId);
    public void addToFavorites(String guestId, Long accommodationId);
    public boolean checkIfInFavorites(String guestId, Long accommodationId);
    public Long reportUser(ReportedUser reportedUser);
    public void removeOwnerReview(Review review);
    public void save(User user);
}
