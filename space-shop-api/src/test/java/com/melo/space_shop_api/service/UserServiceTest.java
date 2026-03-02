package com.melo.space_shop_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.melo.space_shop_api.dto.auth.RequestRegisterDTO;
import com.melo.space_shop_api.dto.user.UserResponseDTO;
import com.melo.space_shop_api.entity.user.User;
import com.melo.space_shop_api.entity.user.UserRole;
import com.melo.space_shop_api.exception.InvalidUserException;
import com.melo.space_shop_api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationService authService;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    private RequestRegisterDTO dtoValid;

    private RequestRegisterDTO dtoInvalid;

    private User user;

    private User authenticatedUser;

    void setUp() {
        dtoValid = new RequestRegisterDTO("jose", "jose@example.com", "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");
        dtoInvalid = new RequestRegisterDTO("", "jose@example.com", "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");
        user = new User(Long.valueOf(1), "jose", "jose@example.com", "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");
        authenticatedUser = new User(Long.valueOf(0), "jose", "root@example.com", "12345678", UserRole.ROLE_ADMIN, "12345678911", "83988887776");
    }

    @Test
    void testAdminRegisterUserWithRoleUser() {
        setUp();

        when(authService.getCurrentUser()).thenReturn(authenticatedUser);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(encoder.encode(anyString())).thenReturn("12345678");

        UserResponseDTO response = userService.register(dtoValid);

        assertEquals(user.getId(), response.id());
        assertEquals(user.getRole(), response.role());
    }

    @Test
    void testAdminRegisterUserWithRoleAdmin() {
        setUp();
        user.setRole(UserRole.ROLE_ADMIN);

        when(authService.getCurrentUser()).thenReturn(authenticatedUser);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(encoder.encode(anyString())).thenReturn("12345678");

        RequestRegisterDTO dto = new RequestRegisterDTO("jose", "jose@example.com", "12345678", UserRole.ROLE_ADMIN, "12345678910", "83988887777");
        UserResponseDTO response = userService.register(dto);

        assertEquals(user.getId(), response.id());
        assertEquals(user.getRole(), response.role());
    }

    @Test
    void testUserRegisterUserWithRoleAdmin() {
        user = new User(Long.valueOf(1), "jose", "jose@example.com", "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");
        User unauthenticatedUser = new User(Long.valueOf(0), "jose", "root@example.com", "12345678", UserRole.ROLE_USER, "12345678911", "83988887776");

        when(authService.getCurrentUser()).thenReturn(unauthenticatedUser);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(encoder.encode(anyString())).thenReturn("12345678");

        RequestRegisterDTO dto = new RequestRegisterDTO("jose", "jose@example.com", "12345678", UserRole.ROLE_ADMIN, "12345678910", "83988887777");
        UserResponseDTO response = userService.register(dto);

        assertEquals(user.getId(), response.id());
        assertEquals(UserRole.ROLE_USER, response.role());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserWithInvalidDataName() {
        setUp();

        RequestRegisterDTO dtoNull = new RequestRegisterDTO(null, "jose@example.com", "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");

        assertThrows(InvalidUserException.class, () -> userService.register(dtoInvalid));
        assertThrows(InvalidUserException.class, () -> userService.register(dtoNull));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUserWithInvalidDataEmail() {
        RequestRegisterDTO dtoNull = new RequestRegisterDTO("jose", null, "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");
        RequestRegisterDTO dtoEmpty = new RequestRegisterDTO("jose", "    ", "12345678", UserRole.ROLE_USER, "12345678910", "83988887777");

        assertThrows(InvalidUserException.class, () -> userService.register(dtoNull));
        assertThrows(InvalidUserException.class, () -> userService.register(dtoEmpty));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUserWithInvalidDataPassword() {
        RequestRegisterDTO dtoNull = new RequestRegisterDTO("jose", "jose@example.com", null, UserRole.ROLE_USER, "12345678910", "83988887777");
        RequestRegisterDTO dtoEmpty = new RequestRegisterDTO("jose", "jose@example.com", "    ", UserRole.ROLE_USER, "12345678910", "83988887777");

        assertThrows(InvalidUserException.class, () -> userService.register(dtoNull));
        assertThrows(InvalidUserException.class, () -> userService.register(dtoEmpty));

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUserWithInvalidDataRole() {
        RequestRegisterDTO dtoNull = new RequestRegisterDTO("jose", "jose@example.com", "12345678", null, "12345678910", "83988887777");

        assertThrows(InvalidUserException.class, () -> userService.register(dtoNull));

        verify(userRepository, times(0)).save(any(User.class));
    }
}
