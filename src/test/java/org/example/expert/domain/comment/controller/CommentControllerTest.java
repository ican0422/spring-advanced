package org.example.expert.domain.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.expert.config.AuthUserArgumentResolver;
import org.example.expert.config.GlobalExceptionHandler;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.dto.AuthUser;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentController controller;

    @MockBean
    private CommentService commentService;

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
    public void 댓글_조회() throws Exception {
        // given
        Long todoId = 1L;

        given(commentService.getComments(anyLong())).willReturn(anyList());

        // when
        ResultActions resultActions = mockMvc.perform(get("/todos/{todoId}/comments", todoId));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 댓글_생성() throws Exception {
        given(resolver.supportsParameter(any())).willReturn(true);
        given(resolver.resolveArgument(any(), any(), any(), any())).willReturn(new AuthUser(1L, "email", UserRole.USER));

        AuthUser authUser = new AuthUser(1L, "email", UserRole.USER);
        long todoId = 1L;
        CommentSaveRequest request = new CommentSaveRequest("contents");

        given(commentService.saveComment(authUser, todoId, request)).willReturn(any());

        // when
        ResultActions resultActions = mockMvc.perform((post("/todos/{todoId}/comments", todoId))
                .header(HttpHeaders.AUTHORIZATION, "test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isOk());
    }
}
