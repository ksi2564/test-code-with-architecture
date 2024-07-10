package com.example.demo.post.service;

import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        postService = PostService.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        User user1 = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test nickname")
                .address("test address")
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("test2@test.com")
                .nickname("test nickname2")
                .address("test address2")
                .certificationCode("aaaa-aaaa-aaaa-bbbb")
                .status(UserStatus.PENDING)
                .lastLoginAt(200L)
                .build();

        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("test content1")
                .createdAt(1678530673958L)
                .modifiedAt(1678530673958L)
                .writer(user1)
                .build());
    }

    @Test
    void getById_는_존재하는_게시물을_내려준다() {
        // given
        // when
        Post result = postService.getById(1);

        // then
        assertThat(result.getContent()).isEqualTo("test content1");
        assertThat(result.getWriter().getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("test content2")
                .build();

        // when
        Post result = postService.create(postCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("test content2");
        assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("updated test content1")
                .build();

        // when
        postService.update(1, postUpdate);

        // then
        Post post = postService.getById(1);
        assertThat(post.getContent()).isEqualTo("updated test content1");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }
}