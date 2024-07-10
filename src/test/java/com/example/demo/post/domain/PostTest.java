package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.service.PostService;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        Post post = Post.from(writer, postCreate, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("new content");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("test@test.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("test1");
        assertThat(post.getWriter().getAddress()).isEqualTo("test address");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaa-aaaa-aaaa-aaaa");
    }

    @Test
    void PostUpdate_으로_게시물_내용을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("updated content")
                .build();
        User writer = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test1")
                .address("test address")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("new content")
                .createdAt(100L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when

        post = post.update(postUpdate, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("updated content");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }
}