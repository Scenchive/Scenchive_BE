package com.example.scenchive.domain.member.service;

import com.example.scenchive.domain.member.exception.NotCorrespondingEmailException;
import com.example.scenchive.domain.member.repository.Member;
import com.example.scenchive.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
//
@Transactional
@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberRepository memberRepository;

    //검증 기능 동작함
//    public Long login(String email, String password) throws NotCorrespondingEmailException {
//        Optional<Member> findMember=memberRepository.findByEmail(email);
//        if(!findMember
//                .orElseThrow(()-> new NotCorrespondingEmailException("해당 이메일이 존재하지 않습니다."))
//                .checkPassword(password)){
//            throw new IllegalStateException("비밀번호가 틀렸습니다.");
//        }
//        return findMember.get().getId();
//    }

}