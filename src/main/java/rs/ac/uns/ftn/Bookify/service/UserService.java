package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Image;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IImageService imageService;

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Collection<UserDTO> getAll() {
        Collection<UserDTO> users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            users.add(new UserDTO(1231L, "test@example.com", "Pera","Peric", false));
        }
        return users;
    }

    @Override
    public UserDetailDTO find(Long userId) {
        return userRepository.findUserAccount(userId);
    }

    @Override
    public Long create(UserRegisteredDTO newUser) {
        newUser.setId(123L);
        return 123L;
    }

    @Override
    public UserDetailDTO update(UserDetailDTO updatedUser) {
        Optional<User> user = this.userRepository.findById(updatedUser.getId());
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User u = user.get();
        updateUserData(updatedUser, u);
        this.userRepository.save(u);
        return new UserDetailDTO(u);
    }

    public boolean changePassword(Long userId, String newPassword){
        Optional<User> u = userRepository.findById(userId);
        if(u.isEmpty()){
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

    @Override
    public Long updateImage(byte[] bytes, String imageName, Long userId) throws Exception {
        Optional<User> u = userRepository.findById(userId);
        if(u.isEmpty()){
            throw new Exception();
        }
        Image image = imageService.save(bytes, "accounts", imageName);
        User user = u.get();
        user.setProfileImage(image);
        userRepository.save(user);
        return image.getId();
    }

    @Override
    public FileSystemResource getImage(Long imageId) {
        return imageService.find(imageId);
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

}
