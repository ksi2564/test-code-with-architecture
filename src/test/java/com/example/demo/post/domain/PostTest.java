package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void PostCreate_으로_게시물을_만들_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("new content")
                .build();
        User writer = User.builder()
                .email("test@test.com")
                .nickname("test1")
                .address("test address")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .build();

        // when
        Post post = Post.from(writer, postCreate);

        // then
        assertThat(post.getContent()).isEqualTo("new content");
        assertThat(post.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("test1");
        assertThat(post.getWriter().getAddress()).isEqualTo("test address");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaa-aaaa-aaaa-aaaa");
    }

    @Test
    void PostUpdate_으로_게시물_내용을_수정할_수_있다() {
        // given

        // when

        // then

    }
}