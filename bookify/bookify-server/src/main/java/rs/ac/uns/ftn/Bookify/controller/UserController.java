package rs.ac.uns.ftn.Bookify.controller;



import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.mapper.ReportedUserDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.UserBasicDTOMapper;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReportedUserService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.*;

import rs.ac.uns.ftn.Bookify.dto.ReportedUserDTO;

@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IReportedUserService reportedUserService;

    private final String IP_ADDRESS = "192.168.1.5";

    @GetMapping(value = "/reported", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<ReportedUserDetailsDTO>> getReportedUsers() {
        List<ReportedUserDetailsDTO> response = new ArrayList<>();
        reportedUserService.getAllReports().forEach(reportedUser -> response.add(new ReportedUserDetailsDTO(reportedUser)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        List<UserDTO> response = new ArrayList<>();
        userService.getAll().forEach((u) ->{
            response.add(new UserDTO(u));
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<UserDetailDTO> getUserById(@PathVariable String userId) {
        Optional<User> user = Optional.ofNullable(userService.get(userId));
        return user.map(userDetailDTO -> new ResponseEntity<>(new UserDetailDTO(user.get()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<UserDetailDTO> updateUser(@RequestBody @Valid UserDetailDTO updatedUser) {
        Optional<User> user = Optional.ofNullable(userService.update(updatedUser));
        return user.map(userDetailDTO -> new ResponseEntity<>(new UserDetailDTO(user.get()), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return new ResponseEntity<>("Account has not been deleted", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{userId}/block-user", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> blockUser(@PathVariable String userId) {
        UserDTO response = this.userService.block(userId);
        if(response != null) {
            reportedUserService.deletedUsersReports(response.getUid());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{userId}/unblock-user", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> unblockUser(@PathVariable String userId) {
        UserDTO response = this.userService.unblock(userId);
        if(response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/report")
//    @PreAuthorize("hasAnyAuthority('ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<Long> insertReport(@Valid @RequestBody ReportedUserDTO dto) {
        //insert new report
        ReportedUser user = ReportedUserDTOMapper.fromDTOtoUser(dto);
        User reportedUser = userService.get(dto.getReportedUser());
        User reportingUser = userService.get(dto.getCreatedBy());
        user.setCreatedBy(reportingUser);
        user.setReportedUser(reportedUser);
        user.setCreated(new Date());

        Long id = userService.reportUser(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping(value = "/change-image/{userId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<Long> changeAccountImage(@RequestParam("image") MultipartFile image, @PathVariable Long userId) throws Exception {
        Long id = userService.updateImage(image.getBytes(), image.getName(), userId);
        if (id < 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<FileSystemResource> getAccountImage(@PathVariable Long imageId) throws Exception {
        FileSystemResource image = userService.getImage(imageId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/account-pic/{userId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_GUEST', 'ROLE_OWNER')")
    public ResponseEntity<Long> getAccountImageId(@PathVariable String userId) throws Exception {
        User u = userService.get(userId);
        Long imageId = -1L;
        if (u.getProfileImage() != null) {
            imageId = u.getProfileImage().getId();
        }
        return new ResponseEntity<>(imageId, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBasicDTO> getUser(@PathVariable String userId) {
        User user = userService.get(userId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserBasicDTO dto = UserBasicDTOMapper.fromOwnertoDTO(user);
        dto.setType(user.getUserType());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

