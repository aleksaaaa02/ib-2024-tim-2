package rs.ac.uns.ftn.Bookify.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.service.ImageService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.Date;
import java.util.Collection;
import java.util.Optional;

import rs.ac.uns.ftn.Bookify.dto.ReportedUserDTO;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;

import java.time.LocalDateTime;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ImageService imageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReportedUserDTO>> getReportedUsers() {
        //return all reported users
        Collection<ReportedUserDTO> reportedUsers = new HashSet<>();
        reportedUsers.add(new ReportedUserDTO("Reason", new Date(), new Owner(), new Guest()));
        reportedUsers.add(new ReportedUserDTO("Reason", new Date(), new Owner(), new Guest()));
        return new ResponseEntity<Collection<ReportedUserDTO>>(reportedUsers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        Collection<UserDTO> response = userService.getAll();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDTO> getUserById(@PathVariable Long userId) {
        Optional<UserDetailDTO> user = Optional.ofNullable(userService.get(userId));
        return user.map(userDetailDTO -> new ResponseEntity<>(userDetailDTO, HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegisteredDTO newUser) {
        Long userId = userService.create(newUser);
        if (userId != null) {
            return new ResponseEntity<>("New user created", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Failed to create new user", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<UserDetailDTO> updateUser(@RequestBody UserDetailDTO updatedUser) {
        Optional<UserDetailDTO> user = Optional.ofNullable(userService.update(updatedUser));
        return user.map(userDetailDTO -> new ResponseEntity<>(userDetailDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long userId, @RequestBody PasswordUpdateDTO newPassword) {
        boolean success = userService.changePassword(userId, newPassword);
        if (success) {
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to change password", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/forgot-password")
    public ResponseEntity<String> forgotPassword(@PathVariable Long userId) {
        return new ResponseEntity<>("Email sent", HttpStatus.OK);
    }

    @PostMapping("/activate-account/{userId}")
    public ResponseEntity<String> activateAccount(@PathVariable Long userId) {
        boolean activated = userService.activateUser(userId);
        if (activated) {
            return new ResponseEntity<>("Account activated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to activate account", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentialsDTO userCredentials) {
        boolean success = userService.login(userCredentials);
        if (success) {
            return new ResponseEntity<>("Login successful!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login failed", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        return new ResponseEntity<>("Account deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/{userId}/block-user")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {

        return new ResponseEntity<>("User blocked successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/report")
    public ResponseEntity<ReportedUserDTO> insertReport(@RequestBody ReportedUserDTO reservation) {
        //insert new report
        ReportedUserDTO reportedUserDTO = new ReportedUserDTO("Reason", new Date(), new Owner(), new Guest());
        return new ResponseEntity<>(reportedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<UserDTO>> searchUsers(@RequestParam String searchParameter) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/change-image/{userId}")
    public ResponseEntity<Long> changeAccountImage(@RequestParam MultipartFile image, @PathVariable Long userId) throws Exception {
        Long id = imageService.save(image.getBytes(), "accounts","test");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<FileSystemResource> getAccountImage(@PathVariable Long imageId) throws Exception {
        FileSystemResource image = imageService.find(imageId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

}

