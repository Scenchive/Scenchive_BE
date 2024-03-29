package com.example.scenchive.domain.member.dto;

import com.example.scenchive.domain.member.repository.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

//
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {

    @NotBlank
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private Set<AuthorityDto> authorityDtoSet;

    public static MemberForm from(Member member) {
        if(member == null) return null;

        return MemberForm.builder()
                .email(member.getEmail())
                .name(member.getName())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
