package com.example.scenchive.domain.member.service;

import com.example.scenchive.domain.member.dto.UtagDto;
import com.example.scenchive.domain.member.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class ProfileService {

    private final UserTagRepository userTagRepository;
    private final MemberRepository memberRepository;
    private final UtagRepository utagRepository;
    private final UtagTypeRepository utagTypeRepository;

    @Autowired
    public ProfileService(UserTagRepository userTagRepository, MemberRepository memberRepository, UtagRepository utagRepository, UtagTypeRepository utagTypeRepository) {
        this.userTagRepository = userTagRepository;
        this.memberRepository=memberRepository;
        this.utagRepository=utagRepository;
        this.utagTypeRepository=utagTypeRepository;
    }

    //향수 프로필 유저 키워드 조회
    @Transactional
    public List<UtagDto> profileGetUtags(Long userId){
        List<UtagDto> utagDtos = new ArrayList<>();
        Member member=memberRepository.findById(userId).get();
        List<UserTag> userTags = userTagRepository.findByMember(member);
        for(UserTag userTag : userTags){
            Long id=userTag.getUtag().getId();
            String utag=userTag.getUtag().getUtag();
            String utag_kr=userTag.getUtag().getUtag_kr();
            int utagtype_id=userTag.getUtag().getUtagtype().getId();

            UtagDto utagDto=new UtagDto(id, utag, utag_kr, utagtype_id);
            utagDtos.add(utagDto);
        }
        return utagDtos;
    }

    //향수 프로필 유저 키워드 수정
    @Transactional
    public String profileKeywordEdit(Long userId, List<UtagDto> utagDtoList){
        Member member=memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("Member not found"));;
        List<UserTag> userTags = userTagRepository.findByMember(member); // 사용자에게 이미 저장돼있는 UserTag 리스트

        List<Utag> existingUtags=new ArrayList<>(); // 사용자에게 이미 저장돼있는 utag를 저장할 리스트
        List<Utag> newUtags=new ArrayList<>(); // 전달받은 utag를 저장할 리스트

        for(UserTag userTag : userTags){
            Utag utag=userTag.getUtag();
            existingUtags.add(utag);
        }

        for(UtagDto utagDto : utagDtoList){
            Long id= utagDto.getId();
            Utag utag=utagRepository.findById(id).get();
            newUtags.add(utag);
        }

        //전달받은 키워드 리스트에는 있는데 기존 리스트에는 없는 경우 저장
        for(UtagDto utagDto : utagDtoList){
            Long id=utagDto.getId();
            Utag utag=utagRepository.findById(id).orElseThrow(() -> new RuntimeException("Utag not found"));
            if(!existingUtags.contains(utag)){
                UserTag userTag=new UserTag(member, utag);
                userTagRepository.save(userTag);
            }
        }

        //전달받은 키워드 리스트에는 없는데 기존 리스트에는 있는 경우 삭제
        for (UserTag userTag : userTags) {
            Utag utag=userTag.getUtag();
            if (!newUtags.contains(utag)) {
                userTagRepository.delete(userTag);
            }
        }
        return "update";
    }

//    //향수 프로필 유저 키워드 저장
//    @Transactional
//    public String profileSave(Long userId, UtagDto utagDto){
//        Member member=memberRepository.findById(userId).get();
//        UtagType utagType=utagTypeRepository.findById(utagDto.getUtagtype_id()).get();
//
//        Utag utag=Utag.builder().id(utagDto.getId())
//                .utag(utagDto.getUtag())
//                .utag_kr(utagDto.getUtag_kr())
//                .utagtype(utagType)
//                .build();
//
//        //중복저장 방지
//        Optional<UserTag> checkUserTag=userTagRepository.findByMemberAndUtag(member, utag);
//        if(checkUserTag.isPresent()){
//            return "already exists";
//        }
//
//        //db에 없는 경우에만 저장
//        UserTag userTag=UserTag.builder()
//                .member(member)
//                .utag(utag)
//                .build();
//
//        userTagRepository.save(userTag);
//        return "save";
//    }
//
//    //향수 프로필 유저 키워드 삭제
//    @Transactional
//    public String profileDelete(Long userId, UtagDto utagDto){
//        Member member=memberRepository.findById(userId).get();
//        Utag utag=utagRepository.findById(utagDto.getId()).get();
//        userTagRepository.deleteByMemberAndUtag(member, utag);
//        return "delete";
//    }

    //향수 프로필 수정하기 클릭 시 키워드 조회
//    public List<List> profileGetAllTags(Long userId){
//        List<UtagDto> userUtagDtos = new ArrayList<>();
//        List<UtagDto> allUtagDtos = new ArrayList<>();
//        List<List> utagDtos=new ArrayList<>();
//
//        Member member=memberRepository.findById(userId).get();
//        List<UserTag> userTags = userTagRepository.findByMember(member);
//        for(UserTag userTag : userTags){
//            Long id=userTag.getUtag().getId();
//            String utag=userTag.getUtag().getUtag();
//            String utag_kr=userTag.getUtag().getUtag_kr();
//            int utagtype_id=userTag.getUtag().getUtagtype().getId();
//
//            UtagDto utagDto=new UtagDto(id, utag, utag_kr, utagtype_id);
//            userUtagDtos.add(utagDto);
//        }
//
//        utagDtos.add(userUtagDtos);
//
//        for (long i = 1; i <= 25; i++) {
//            UtagDto utagDto = new UtagDto(i,
//                    utagRepository.findById(i).get().getUtag(),
//                    utagRepository.findById(i).get().getUtag_kr(),
//                    utagRepository.findById(i).get().getUtagtype().getId());
//            allUtagDtos.add(utagDto);
//        }
//
//        utagDtos.add(allUtagDtos);
//
//        return utagDtos;
//    }
}
