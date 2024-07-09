package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MyProfileResponseTest {

    @Test
    void User_로_내_프로필_응답을_생성할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("test1")
                .address("test address")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaaa-aaaa-aaaa")
                .lastLoginAt(100L)
                .build();

        // when
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then
        assertThat(myProfileResponse.getId()).isEqualTo(1);
        assertThat(myProfileResponse.getEmail()).isEqualTo("test@test.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("test1");
        assertThat(myProfileResponse.getAddress()).isEqualTo("test address");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }

}