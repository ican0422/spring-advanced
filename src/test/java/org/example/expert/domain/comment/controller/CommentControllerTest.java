package org.example.expert.domain.comment.controller;

import org.example.expert.domain.comment.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CommentControllerTest.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void 댓글_조회() throws Exception {
        long todoId = 1L;

        given(commentService.getComments(anyLong())).willReturn(List.of());
        
        mockMvc.perform(get("/todos/{todoId}/comments"));


    }
}
