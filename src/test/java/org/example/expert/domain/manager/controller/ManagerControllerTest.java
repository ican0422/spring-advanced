package org.example.expert.domain.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.expert.config.AuthUserArgumentResolver;
import org.example.expert.config.GlobalExceptionHandler;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.service.ManagerService;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManagerController controller;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private JwtUtil jwtUtil;

    @Mock
    private AuthUserArgumentResolver resolver;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(resolver)
                .build();
    }

    @Test
    public void 매니저_생성() throws Exception {
        // given
        given(resolver.supportsParameter(any())).willReturn(true);
        given(resolver.resolveArgument(any(), any(), any(), any())).willReturn( new AuthUser(1L, "email", UserRole.USER));

        AuthUser authUser = new AuthUser(1L, "email", UserRole.USER);
        long todoId = 1L;
        ManagerSaveRequest request = new ManagerSaveRequest(1L);
        given(managerService.saveManager(authUser, todoId, request)).willReturn(any());

        // when
        ResultActions resultActions = mockMvc.perform((post("/todos/{todoId}/managers", todoId))
                .header(HttpHeaders.AUTHORIZATION, "test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 매니저_조회() throws Exception {
        // given
        long todoId = 1L;

        given(managerService.getManagers(anyLong())).willReturn(List.of());

        // when
        ResultActions resultActions = mockMvc.perform(get("/todos/{todoId}/managers", todoId));

        // then
        resultActions.andExpect(status().isOk());
    }
}
