package com.ngsolutions.SmartMall.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngsolutions.SmartMall.model.dto.user.UserDTOFromJson;
import com.ngsolutions.SmartMall.model.dto.user.UserEditDTO;
import com.ngsolutions.SmartMall.model.dto.user.UserEditRolesDTO;
import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationDTO;
import com.ngsolutions.SmartMall.model.entity.Role;
import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.model.event.UserRegisteredEvent;
import com.ngsolutions.SmartMall.repo.RoleRepository;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.UserService;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher appEventPublisher;
    private final UserDetailsService smartMallUserDetailsService;
    private final ImageEncryptor imageEncryptor;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder,
            ApplicationEventPublisher appEventPublisher,
            UserDetailsService userDetailsService, ImageEncryptor imageEncryptor) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
        this.smartMallUserDetailsService = userDetailsService;
        this.imageEncryptor = imageEncryptor;
    }

    @Override
    public void registerUser(
            UserRegistrationDTO userRegistrationDTO) {

        userRepository.save(mapUserRegistrationDTOToUser(userRegistrationDTO));

        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",
                userRegistrationDTO.email(),
                userRegistrationDTO.username()
        ));
    }

    @Override
    public void updateUser(UserEditDTO userEditDTO) throws IOException {
        User user = userRepository.findById(userEditDTO.getId()).get();

        User editedUser = mapUserEditDTOToUser(userEditDTO);
        if (editedUser.getShopPhoto() == null) {
            editedUser.setShopPhoto(user.getShopPhoto());
        }
        userRepository.save(editedUser);
    }

    @Override
    public void updateUsersRoles(List<UserEditRolesDTO> usersEdit) {
        for (UserEditRolesDTO userEdit : usersEdit) {
            User user = userRepository.findById(userEdit.getId()).get();
            user.setRoles(userEdit.getRoles());
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserRoles(String json) {
        UserDTOFromJson[] data = null;

        try {
            data = new ObjectMapper().readValue(json, UserDTOFromJson[].class);
        } catch (Exception e) {
        }

        for (UserDTOFromJson userEdit : data) {
            User user = userRepository.findById(Long.parseLong(userEdit.id)).get();
            List<Role> roles = new ArrayList<>();
            for (String roleId :
                    userEdit.roles) {
                roles.add(this.roleRepository.findById(Long.parseLong(roleId)).get());
            }
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Override
    public UserEditDTO getUserEditDTOByEmail(String email) throws IOException {
        User user = userRepository.findByEmail(email).get();

        return mapUserToUserEditDTO(user);
    }

    @Override
    public List<UserEditRolesDTO> getUserEditRolesDTOs() {
        return userRepository.findAll().stream().map(this::mapUserToUserEditRolesDTO).toList();
    }

    @Override
    public void createUserIfNotExist(String email, String names) {
        // Create manually a user in the database
        // password not necessary
    }

    @Override
    public Authentication login(String email) {
        UserDetails userDetails = smartMallUserDetailsService.loadUserByUsername(email);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    private User mapUserRegistrationDTOToUser(UserRegistrationDTO userRegistrationDTO) {
        return new User()
                .setActive(true)
                .setUsername(userRegistrationDTO.username())
                .setEmail(userRegistrationDTO.email())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
    }

    private User mapUserEditDTOToUser(UserEditDTO userEditDTO) throws IOException {
        User user = new User()
                .setActive(true)
                .setUsername(userEditDTO.getUsername())
                .setEmail(userEditDTO.getEmail())
                .setPassword(userEditDTO.getNewPassword().trim().isEmpty() ? userEditDTO.getPassword() : passwordEncoder.encode(userEditDTO.getNewPassword()))
                .setShopPhoto(userEditDTO.getShopPhoto().isEmpty() ? null : this.imageEncryptor.EncryptImage(userEditDTO.getShopPhoto()))
                .setRoles(userEditDTO.getRoles())
                .setAddress(userEditDTO.getAddress())
                .setPhoneNumber(userEditDTO.getPhoneNumber())
                .setShopName(userEditDTO.getShopName());

        user.setId(userEditDTO.getId());
        return user;
    }

    private UserEditDTO mapUserToUserEditDTO(User user) throws IOException {
        UserEditDTO userEditDTO = new UserEditDTO();

        userEditDTO.setId(user.getId());
        userEditDTO.setActive(userEditDTO.isActive());
        userEditDTO.setUsername(user.getUsername());
        userEditDTO.setEmail(user.getEmail());
        userEditDTO.setPassword(user.getPassword());
        userEditDTO.setDisplayShopPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(user.getShopPhoto()));
        userEditDTO.setRoles(user.getRoles());
        userEditDTO.setAddress(user.getAddress());
        userEditDTO.setPhoneNumber(user.getPhoneNumber());
        userEditDTO.setShopName(user.getShopName());

        return userEditDTO;
    }

    private UserEditRolesDTO mapUserToUserEditRolesDTO(User user) {
        UserEditRolesDTO userEditRolesDTO = new UserEditRolesDTO();

        userEditRolesDTO.setId(user.getId());
        userEditRolesDTO.setUsername(user.getUsername());
        userEditRolesDTO.setRoles(user.getRoles());

        return userEditRolesDTO;
    }
}
