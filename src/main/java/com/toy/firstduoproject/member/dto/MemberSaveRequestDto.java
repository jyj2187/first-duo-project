package com.toy.firstduoproject.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class MemberSaveRequestDto {

    @NotBlank
    @Size(min = 2)
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotEmpty
    @Size(min = 2)
    private String nickname;
    @Email
    private String email;

    public MemberSaveRequestDto(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

//    public Member toEntity() {
//        return Member.builder()
//                .username(username)
//                .password(password)
//                .nickname(nickname)
//                .email(email)
//                .role("ROLE_USER")
//                .build();
//    }
}
