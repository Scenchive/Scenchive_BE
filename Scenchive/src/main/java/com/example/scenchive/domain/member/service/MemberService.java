package com.example.scenchive.domain.member.service;

import com.example.scenchive.domain.member.dto.MemberForm;
import com.example.scenchive.domain.member.repository.Member;
import com.example.scenchive.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long save(MemberForm memberForm){ //Id를 반환할지 member 객체 자체를 반환할지 고민
        validDuplicateMember(memberForm);
        return memberRepository.save(memberForm.toEntity()).getId();
    }

    //name 중복 확인
    private void validDuplicateMember(MemberForm memberForm){
        List<Member> findMembers=memberRepository.findByName(memberForm.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
}