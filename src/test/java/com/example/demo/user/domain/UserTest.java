package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    void UserCreate_객체로_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("test@test.com")
                .nickname("test nickname")
                .address("test address")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaa-aaaa-aaaa-aaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getNickname()).isEqualTo("test nickname");
        assertThat(user.getAddress()).isEqualTo("test address");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaa-aaaa-aaaa-aaaa");
    }

    @Test
    void UserUpdate_객체로_데이터를_수정할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test nickname")
                .address("test address")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("updated test nickname")
                .address("updated test address")
                .build();

        // when
        user = user.update(userUpdate);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getNickname()).isEqualTo("updated test nickname");
        assertThat(user.getAddress()).isEqualTo("updated test address");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
        assertThat(user.getCertificationCode()).isEqualTo("aaaa-aaaa-aaaa-aaaa");
    }

    @Test
    void 로그인을_할_수_있고_로그인_시_마지막_로그인_시간이_변경된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test nickname")
                .address("test address")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test nickname")
                .address("test address")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .build();

        // when
        user = user.certificate("aaaa-aaaa-aaaa-aaaa");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test nickname")
                .address("test address")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .build();

        // when
        // then
        assertThatThrownBy(() -> user.certificate("it-is-not-certification-code"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}