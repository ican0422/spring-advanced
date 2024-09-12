package org.example.expert.domain.user;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordEncoder newPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void 비밀번호_변경_검증() {
        // given
        long userId = 1L;
        String oldPassword = "Asd12345!";
        String newPassword = "Asfg12345!";
        String encodedOldPassword = newPasswordEncoder.encode(oldPassword);
        String encodedNewPassword = newPasswordEncoder.encode(newPassword);

        User user = new User(userId, "test@a.com", encodedOldPassword, UserRole.USER);
        UserChangePasswordRequest request = new UserChangePasswordRequest(oldPassword, newPassword);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.matches(newPassword, encodedOldPassword)).thenReturn(false);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        // Act
        assertDoesNotThrow(() -> userService.changePassword(userId, request));

        // then
        // User 엔티티의 비밀번호가 변경되었는지 확인
        assertEquals(encodedNewPassword, user.getPassword());
    }

    @Test
    public void 유저_조회_중_찾지_못해_에러가_발생한다() {
        // given
        long userId = 1L;

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            userService.getUser(userId);
        });
        // then
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void 유저_조회_정상() {
        // given
        long userId = 1L;
        User user = new User(userId,"a@a.com","Asd12345", UserRole.USER);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when
        UserResponse userResponse = userService.getUser(userId);

        // then
        assertEquals(userResponse.getId(), user.getId());
        assertEquals(userResponse.getEmail(), user.getEmail());
    }
}
