package org.example.expert.domain.user;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    @Spy
    private UserService userService;

    @Test
    public void 비밀번호_변경_검증() {
        // given
        long userId = 1L;
        User user = new User("test@a.com", passwordEncoder.encode("Asd12345"), UserRole.USER);

        // when

        // then
    }
}
