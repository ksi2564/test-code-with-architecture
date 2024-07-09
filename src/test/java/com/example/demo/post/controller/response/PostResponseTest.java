package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    void Post_로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .content("new content")
                .writer(User.builder()
                        .email("test@test.com")
                        .nickname("test1")
                        .address("test address")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaa-aaaa-aaaa-aaaa")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("new content");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("test1");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}