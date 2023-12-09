package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IImageService imageService;

    private final IUserRepository userRepository;
    private final IReservationService reservationService;

    @Autowired
    public UserService(IImageService imageService, IUserRepository userRepository, IReservationService reservationService) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
    }


    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public User get(Long userId) {
        Optional<User> u = userRepository.findById(userId);
        return u.orElse(null);
    }

    @Override
    public Long create(UserRegisteredDTO newUser) {
        newUser.setId(123L);
        return 123L;
    }

    @Override
    public User update(UserDetailDTO updatedUser) {
        Optional<User> user = this.userRepository.findById(updatedUser.getId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User u = user.get();
        updateUserData(updatedUser, u);
        try {
            userRepository.save(u);
        } catch (Exception e) {
            return null;
        }
        return u;
    }

    public boolean changePassword(Long userId, String newPassword) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) {
            return false;
        }
        User user = u.get();
        user.setPassword(newPassword); // TO-DO hash the password before storing it in database
        userRepository.save(user);
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
    public boolean delete(Long userId) {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) {
            return false;
        }
        return deleteUser(u.get());
    }

    private boolean deleteUser(User user) {
        if(deletionIsPossibility(user)) {
            userRepository.deleteById(user.getId());
            return true;
        }
        return false;
    }

    private String getRole(User user) {
        String role;
        if (user instanceof Owner) {
            role = "OWNER";
        } else if (user instanceof Admin) {
            role = "ADMIN";
        } else {
            role = "GUEST";
        }
        return role;
    }

    @Override
    public boolean block(Long userId) {
        return false;
    }

    @Override
    public Collection<User> searchUsers(String searchParam) {
        return null;
    }

    @Override
    public Long updateImage(byte[] bytes, String imageName, Long userId) throws Exception {
        Optional<User> u = userRepository.findById(userId);
        if (u.isEmpty()) {
            throw new Exception();
        }
        Image image = imageService.save(bytes, "accounts", imageName);
        User user = u.get();
        user.setProfileImage(image);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return -1L;
        }
        return image.getId();
    }

    @Override
    public FileSystemResource getImage(Long imageId) {
        return imageService.find(imageId);
    }

    @Override
    public OwnerDTO findbyAccommodationId(Long id) {
        Owner o = userRepository.findByAccommodations_Id(id);
        float avgRating = 0;
        Long imageId = 0L;
        if (o.getProfileImage() != null)
            imageId = o.getProfileImage().getId();
        return new OwnerDTO(o.getId(), o.getFirstName(), o.getLastName(), o.getPhone(), avgRating, imageId);
    }

    private void updateUserData(UserDetailDTO updatedUser, User u) {
        u.getAddress().setAddress(updatedUser.getAddress().getAddress());
        u.getAddress().setCity(updatedUser.getAddress().getCity());
        u.getAddress().setZipCode(updatedUser.getAddress().getZipCode());
        u.getAddress().setCountry(updatedUser.getAddress().getCountry());
        u.setPhone(updatedUser.getPhone());
        u.setFirstName(updatedUser.getFirstName());
        u.setLastName(updatedUser.getLastName());
    }

    private boolean deletionIsPossibility(User user) {
        switch (getRole(user)) {
            case "OWNER":
                for(Accommodation acc :((Owner)user).getAccommodations()){
                    if(reservationService.hasFutureReservationsAccommodation(acc)) return false;
                }
                return true;
            case "GUEST":
                return !reservationService.hasFutureReservationsGuest(user.getId());
            default:
                return false;
        }
    }
}
