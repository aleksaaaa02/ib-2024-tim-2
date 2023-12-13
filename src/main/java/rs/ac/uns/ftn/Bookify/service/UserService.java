package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.exception.UserDeletionException;
import rs.ac.uns.ftn.Bookify.exception.UserIsBlockedException;
import rs.ac.uns.ftn.Bookify.exception.UserNotActivatedException;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IImageService imageService, IUserRepository userRepository, IReservationService reservationService,
                       PasswordEncoder passwordEncoder) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public User get(String email) {
        return userRepository.findByEmail(email);
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
        user.setPassword(passwordEncoder.encode(newPassword)); // TO-DO hash the password before storing it in database
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
    public boolean isLoginAvailable(Long userId) {
        User user = get(userId);
        if(user.isBlocked()) {
            throw new UserIsBlockedException();
        }
        if(!user.getActive().isActive()) {
            throw new UserNotActivatedException();
        }
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

    @Override
    public String getRole(User user) {
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

    @Override
    public Float getAvgRating(Long id) {
        Float f = this.userRepository.getAverageReviewByOwnerId(id);
        if (f == null)
            return 0f;
        else
            return f.floatValue();
    }

    @Override
    public void saveOwnerAccommodation(Accommodation accommodation, Long ownerId) {
        Owner owner = userRepository.findOwnerById(ownerId);
        owner.getAccommodations().add(accommodation);
        userRepository.save(owner);
    }

    @Override
    public OwnerDTO setOwnerForAccommodation(Long id) {
        OwnerDTO o = findbyAccommodationId(id);
        o.setAvgRating(getAvgRating(o.getId()));
        return o;
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
                    if(reservationService.hasFutureReservationsAccommodation(acc)) throw new UserDeletionException("Some of your accommodations have active future reservations.");
                }
                return true;
            case "GUEST":
                if(reservationService.hasFutureReservationsGuest(user.getId())) throw new UserDeletionException("You have active future reservations.");
                return true;
            default:
                throw new UserDeletionException("Administrator account can't be deleted");
        }
    }
}
