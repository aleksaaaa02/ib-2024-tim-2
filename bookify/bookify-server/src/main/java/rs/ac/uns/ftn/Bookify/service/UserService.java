package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.exception.UserDeletionException;
import rs.ac.uns.ftn.Bookify.exception.UserIsBlockedException;
import rs.ac.uns.ftn.Bookify.exception.UserNotActivatedException;
import rs.ac.uns.ftn.Bookify.mapper.UserRegisteredDTOMapper;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReportedUserRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.*;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserService implements IUserService {
    private final IImageService imageService;
    private final IUserRepository userRepository;
    private final IReportedUserRepository reportedUserRepository;
    private final IReservationRepository reservationRepository;
    private final IReservationService reservationService;
    private final IAccommodationService accommodationService;
    private final ILdapService ldapService;


    @Autowired
    public UserService(IImageService imageService, IUserRepository userRepository, IReservationService reservationService,
                       @Lazy IAccommodationService accommodationService, IReservationRepository reservationRepository,
                       IReportedUserRepository reportedUserRepository, ILdapService ldapService) {
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
        this.accommodationService = accommodationService;
        this.reservationRepository = reservationRepository;
        this.reportedUserRepository = reportedUserRepository;
        this.ldapService = ldapService;
    }


    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.removeIf(user -> user instanceof Admin);
        return users;
    }

    @Override
    public User get(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByUID(String userId) {
        Optional<User> u = userRepository.findUserByUid(userId);
        return u.orElse(null);
    }

    @Override
    public User create(UserRegisteredDTO newUser) {
        User user = userRepository.findByEmail(newUser.getEmail());
        if (user != null) {
            throw new BadRequestException("Already exists.");
        }
        if (newUser.getRole().equals("owner")) {
            user = UserRegisteredDTOMapper.fromDTOtoOwner(newUser);
        } else {
            user = UserRegisteredDTOMapper.fromDTOtoGuest(newUser);
        }
        Active active = new Active(false, new Date(), UUID.randomUUID().toString());
//        user.setActive(active);
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(UserDetailDTO updatedUser) {
        Optional<User> user = this.userRepository.findUserByUid(updatedUser.getUid());
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
        userRepository.save(user);
        return true;
    }

    @Override
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new BadRequestException("Not found");
        }
        StringBuilder randomPassword = generaterandomPassword();
        userRepository.save(user);
        return randomPassword.toString();
    }

    private static StringBuilder generaterandomPassword() {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=";
        String allChars = upperCaseChars + lowerCaseChars + numberChars + specialChars;
        SecureRandom random = new SecureRandom();
        StringBuilder randomString = new StringBuilder();

        randomString.append(getRandomChar(upperCaseChars, random));
        randomString.append(getRandomChar(lowerCaseChars, random));
        randomString.append(getRandomChar(numberChars, random));
        randomString.append(getRandomChar(specialChars, random));

        int remainingLength = 8;
        while (remainingLength-- > 0) {
            randomString.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return randomString;
    }

    private static char getRandomChar(String charSet, SecureRandom random) {
        return charSet.charAt(random.nextInt(charSet.length()));
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
    public UserDTO block(String userId) {
        Optional<User> u = userRepository.findUserByUid(userId);
        if (u.isEmpty()) throw new BadRequestException("User not found");
        User user = u.get();

        if (user.isBlocked()) throw new BadRequestException("User is already blocked");

        return new UserDTO(block(user));

    }

    @Override
    public UserDTO unblock(String userId) {
        Optional<User> u = userRepository.findUserByUid(userId);
        if (u.isEmpty()) throw new BadRequestException("User not found");
        User user = u.get();

        if(!user.isBlocked()) throw new BadRequestException("User is not blocked");
        return new UserDTO(unblock(user));
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
        return new OwnerDTO(o.getUid(), o.getFirstName(), o.getLastName(), o.getPhone(), avgRating, imageId);
    }

    @Override
    public Float getAvgRating(String id) {
        Float f = this.userRepository.getAverageReviewByOwnerId(id);
        if (f == null)
            return 0f;
        else
            return f.floatValue();
    }

    @Override
    public void saveOwnerAccommodation(Accommodation accommodation, String ownerId) {
        Owner owner = userRepository.findOwnerById(ownerId);
        owner.getAccommodations().add(accommodation);
        userRepository.save(owner);
    }

    @Override
    public OwnerDTO setOwnerForAccommodation(Long id) {
        OwnerDTO o = findbyAccommodationId(id);
        o.setAvgRating(getAvgRating(o.getUid()));
        return o;
    }

    @Override
    @Transactional
    @Scheduled(cron = "${activation.cron}")
    public void checkInactiveUsers() {

    }

    @Override
    public List<AccommodationRequestDTO> findAccommodationRequests() {
        List<AccommodationRequestDTO> response = new ArrayList<>();
        for (Owner owner : userRepository.findAllOwners()) {
            for (Accommodation accommodation : owner.getAccommodations()) {
                if (!isRequestReactedOn(accommodation)) response.add(new AccommodationRequestDTO(owner, accommodation));
            }
        }
        return response;
    }

    @Override
    public Owner getOwner(String ownerId) {
        return userRepository.findOwnerById(ownerId);
    }

    @Override
    public Guest getGuest(String guestId) {
        return userRepository.findGuestById(guestId);
    }

    @Override
    public void saveOwner(Owner owner) {
        userRepository.save(owner);
    }

    @Override
    public UserReservationDTO getOwnerForReservation(Long accommodationId) {
        Owner owner = userRepository.getOwner(accommodationId);
        UserReservationDTO user = new UserReservationDTO();
        user.setUid(owner.getUid());
        user.setFirstName(owner.getFirstName());
        user.setLastName(owner.getLastName());
        user.setAvgRating(getAvgRating(owner.getUid()));
        return user;
    }

    @Override
    public UserReservationDTO getGuestForReservation(Long reservationId) {
        Guest guest = reservationRepository.findById(reservationId).get().getGuest();
        UserReservationDTO user = new UserReservationDTO();
        user.setUid(guest.getUid());
        user.setFirstName(guest.getFirstName());
        user.setLastName(guest.getLastName());
        user.setCancellationTimes(reservationRepository.getCancellationTimes(user.getUid()));
        return user;
    }

    @Transactional
    @Override
    public void addToFavorites(String guestId, Long accommodationId) {
        userRepository.addFavoriteToUser(guestId, accommodationId);
    }


    @Override
    public Long reportUser(ReportedUser reportedUser) {
        Guest guest;
        Owner owner;
        if(reportedUser.getReportedUser() instanceof Guest){
            guest = (Guest) reportedUser.getReportedUser();
            owner = (Owner) reportedUser.getCreatedBy();
            if(reservationService.getReservations(guest.getUid(), owner.getUid()).isEmpty()){
                throw new BadRequestException("Do not have reservations");
            }
        }else{
            owner = (Owner) reportedUser.getReportedUser();
            guest = (Guest) reportedUser.getCreatedBy();
            if(reservationService.getReservations(guest.getUid(), owner.getUid()).isEmpty()){
                throw new BadRequestException("Do not have reservations");
            }
        }
        return reportedUserRepository.save(reportedUser).getId();
    }

    @Override
    public void removeOwnerReview(Review review) {
        Owner owner = userRepository.getOwnerByReview(review);
        owner.getReviews().remove(review);
        userRepository.save(owner);
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }


    @Override
    public boolean checkIfInFavorites(String guestId, Long accommodationId) {
        return userRepository.checkIfInFavorites(guestId, accommodationId) == 1;
    }

    private void updateUserData(UserDetailDTO updatedUser, User u) {
        if(u.getAddress()==null) u.setAddress(new Address());
        u.getAddress().setAddress(updatedUser.getAddress().getAddress());
        u.getAddress().setCity(updatedUser.getAddress().getCity());
        u.getAddress().setZipCode(updatedUser.getAddress().getZipCode());
        u.getAddress().setCountry(updatedUser.getAddress().getCountry());
        u.setPhone(updatedUser.getPhone());
        u.setFirstName(updatedUser.getFirstName());
        u.setLastName(updatedUser.getLastName());
    }

    private boolean deletionIsPossibility(User user) {
        switch (user.getUserType()) {
            case "OWNER":
                List<Long> accId = new ArrayList<>();
                for (Accommodation acc : ((Owner) user).getAccommodations()) {
                    if (reservationService.hasFutureReservationsAccommodation(acc)) {
                        throw new UserDeletionException("Some of your accommodations have active future reservations.");
                    }
                    accId.add(acc.getId());
                }
                accId.forEach(this.accommodationService::deleteAccommodation);
                return true;
            case "GUEST":
                if (reservationService.hasFutureReservationsGuest(user.getUid()))
                    throw new UserDeletionException("You have active future reservations.");
                return true;
            default:
                throw new UserDeletionException("Administrator account can't be deleted");
        }
    }

    private boolean isRequestReactedOn(Accommodation a) {
        return a.getStatus().toString().equals("APPROVED") || a.getStatus().toString().equals("REJECTED");
    }

    private User unblock(User user) {
        String role = user.getUserType();
        if (role.equals("ADMIN")) throw new BadRequestException("Administrator's account cannot be blocked/unblocked");
        user.setBlocked(false);
        return userRepository.save(user);
    }

    private User block(User user) {
        String role = user.getUserType();
        if (role.equals("ADMIN")) throw new BadRequestException("Administrator's account cannot be blocked/unblocked");
        if (role.equals("GUEST")) {
            reservationService.cancelGuestsReservations(user.getUid());
        }
        user.setBlocked(true);
        return userRepository.save(user);
    }

    @Scheduled(fixedRate = 60000)
    public void fetchDataFromLDAP(){
        List<User> users = ldapService.getAllUsersFromLdap();
        for(User u : users){
            Optional<User> user = userRepository.findUserByUid(u.getUid());
            if(user.isEmpty()) userRepository.save(u);
            else {
                // mozda moze i samo da se preskoci :D
                User oldUser = user.get();
                oldUser.setFirstName(u.getFirstName());
                oldUser.setLastName(u.getLastName());
                userRepository.save(oldUser);
            }
        }
    }
}
