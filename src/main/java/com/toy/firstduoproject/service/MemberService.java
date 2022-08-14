package com.toy.firstduoproject.service;

import com.toy.firstduoproject.domain.entity.Member;
import com.toy.firstduoproject.handler.ex.ExistException;
import com.toy.firstduoproject.handler.ex.NoPermissionException;
import com.toy.firstduoproject.repository.MemberRepository;
import com.toy.firstduoproject.service.dto.MemberSaveRequestDto;
import com.toy.firstduoproject.service.dto.MemberUpdateDto;
import com.toy.firstduoproject.service.file.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileStore fileStore;

    public void createMember(MemberSaveRequestDto memberSaveRequestDto) {
        isExistMember(memberSaveRequestDto.getUsername());

        Member member = Member.builder()
                .username(memberSaveRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(memberSaveRequestDto.getPassword()))
                .nickname(memberSaveRequestDto.getNickname())
                .email(memberSaveRequestDto.getEmail())
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다.") );
    }

    public void updateMember(Long memberId, MemberUpdateDto memberUpdateDto) throws IOException {
        String storeFilename = fileStore.storeFile(memberUpdateDto.getProfileImage());
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.update(memberUpdateDto.getNickname(), memberUpdateDto.getEmail(), storeFilename);
        memberRepository.save(member);
    }


    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public void isExistMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if(member.isPresent()) {
            throw new ExistException("이미 존재하는 회원입니다.");
        }
    }
}
