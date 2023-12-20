package com.ngsolutions.SmartMall;

import com.ngsolutions.SmartMall.model.dto.user.*;
import com.ngsolutions.SmartMall.model.entity.*;
import com.ngsolutions.SmartMall.repo.*;
import com.ngsolutions.SmartMall.service.UserService;
import com.ngsolutions.SmartMall.service.impl.*;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationEventPublisher appEventPublisher;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private ImageEncryptor imageEncryptor;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO("test@example.com", "testuser", "testpassword", "testpassword");

        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");

        userService.registerUser(registrationDTO);

        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verify(appEventPublisher, times(1)).publishEvent(Mockito.any());
    }

    @Test
    void testUpdateUser() throws IOException {
        // Given
        UserEditDTO userEditDTO = new UserEditDTO();
        userEditDTO.setId(1L);
        userEditDTO.setNewPassword("newPassword");

        // When
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(new User()));
        userService.updateUser(userEditDTO);

        // Then
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateUsersRoles() {
        UserEditRolesDTO userEditRolesDTO = new UserEditRolesDTO();
        userEditRolesDTO.setId(1L);
        userEditRolesDTO.setRoles(new ArrayList<>());

        List<UserEditRolesDTO> usersEdit = new ArrayList<>();
        usersEdit.add(userEditRolesDTO);

        User existingUser = new User();
        existingUser.setId(1L);

        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(existingUser);

        userService.updateUsersRoles(usersEdit);

        assertEquals(userEditRolesDTO.getRoles(), existingUser.getRoles());
    }

    @Test
    public void testUpdateUserRoles() {
        UserDTOFromJson userDTOFromJson = new UserDTOFromJson();
        userDTOFromJson.id = "1";
        userDTOFromJson.roles = List.of(new String[]{"1", "2"});

        String json = "[{\"id\":\"1\",\"roles\":[\"1\",\"2\"]}]";

        User existingUser = new User();
        existingUser.setId(1L);

        Role role1 = new Role();
        role1.setId(1L);

        Role role2 = new Role();
        role2.setId(2L);

        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(role1), Optional.of(role2));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(existingUser);

        userService.updateUserRoles(json);

        assertEquals(2, existingUser.getRoles().size());
    }

    @Test
    public void testGetUserEditDTOByEmail() throws IOException {
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserEditDTO result = userService.getUserEditDTOByEmail(email);

        assertEquals(email, result.getEmail());
    }

    @Test
    public void testGetUserEditRolesDTOs() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserEditRolesDTO> result = userService.getUserEditRolesDTOs();

        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
        assertEquals(user.getUsername(), result.get(0).getUsername());
    }

    @Test
    void testCreateUserIfNotExist() {
        // Arrange
        String email = "test@example.com";
        String names = "Test User";

        UserRepository userRepository = Mockito.mock(UserRepository.class);

        UserService userService = new UserServiceImpl(
                userRepository, roleRepository, passwordEncoder, appEventPublisher, userDetailsService,imageEncryptor);

        // Act
        userService.createUserIfNotExist(email, names);

        // Assert
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    public void testLogin() {
        String email = "test@example.com";

        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

        Authentication result = userService.login(email);

        assertEquals(userDetails, result.getPrincipal());
    }

}

