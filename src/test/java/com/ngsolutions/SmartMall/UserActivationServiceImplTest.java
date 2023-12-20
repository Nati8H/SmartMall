package com.ngsolutions.SmartMall;

import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.model.entity.UserActivationCodeEntity;
import com.ngsolutions.SmartMall.model.event.UserRegisteredEvent;
import com.ngsolutions.SmartMall.repo.UserActivationCodeRepository;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.EmailService;
import com.ngsolutions.SmartMall.service.UserActivationService;
import com.ngsolutions.SmartMall.service.UserService;
import com.ngsolutions.SmartMall.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserActivationServiceImplTest {

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserActivationCodeRepository userActivationCodeRepository;

    @InjectMocks
    private UserActivationService userActivationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserRegistered() {
        // Arrange
        UserRegisteredEvent event = new UserRegisteredEvent(UserService.class, "test@email.com", "TestUser");
        when(userRepository.findByEmail(event.getUserEmail())).thenReturn(this.createMockUserEntity());
        doNothing().when(emailService).sendRegistrationEmail(any(), any(), any());

        // Act
        userActivationService.userRegistered(event);

        // Assert
        verify(emailService, times(1)).sendRegistrationEmail(eq(event.getUserEmail()), eq(event.getUsername()), any());
        verify(userActivationCodeRepository, times(1)).save(any(UserActivationCodeEntity.class));
    }

    private Optional<User> createMockUserEntity() {
        User userEntity = new User();
        userEntity.setId(1L);
        return Optional.of(userEntity);
    }
}
