package com.yanolja.clone.lego.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.repository.MemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class SignUpService implements UserDetailsService {
    // MemberRepository 를 사용하기 위해 final 로 미리 객체를 생성
    private final MemberRepository memberRepository;

    // UserDetailService 를 상속받아 기능 작성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // username 으로 member 객체 검색
        Member member = memberRepository.findById(username);
        // 만약 member 객체가 null 이라면 오류 던지기
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }
        // User 클래스로 member 의 id 와 비밀번호, 권한을 가진 객체로 빌드
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRollName())
                .build();
    }

    // member 유저 회원가입
    public String signUp(Member member, PasswordEncoder passwordEncoder){
        // member 객체의 비밀번호 암호화
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        // member 객체의 권한 설정
        member.setRollName("USER");
        // member profileImage 설정
        member.setProfileImage("");
        // member 객체를 데이터베이스에 저장
        Member signUpMember = memberRepository.save(member);
        // 데이터베이스에 저장되고 객체를 가져왔을 때 값이 제대로 저장되지 않았다면 'no' 라는 값을 리턴
        if(signUpMember == null){
            return "no";
        }else{
            // 값이 제대로 저장되었다면 'yes' 라는 값을 리턴
            return "yes";
        }
    }

    // member 유저 검색
    public Member findUser(String id){
        // 파라미터로 들어온 id 값으로 member 유저 검색
        Member findMember = memberRepository.findById(id);
        // 값이 없다면 null 값을 리턴
        if(findMember == null){
            return null;
        }
        // 값이 있다면 member 객체 리턴
        return findMember;
    }

    // 아이디 중복 비교
    public String idCheck(String id){
        // 파라미터로 들어온 id 값으로 member 유저 검색
        Member member = memberRepository.findById(id);
        // 값이 있다면 유저가 이미 있는 것이기 때문에 회원가입을 하지 못하도록 'no' 라는 값을 리턴
        if(member != null){
            return "no";
        }
        // 값이 없다면 유저가 없는 것이기 때문에 회원가입을 할 수 있도록 'yes' 라는 값을 리턴
        return "yes";
    }

    // 닉네임 중복 비교
    public String nicknameCheck(String nickname){
        Member member = memberRepository.findByNickname(nickname);
        if(member != null){
            return "no";
        }
        return "yes";
    }

    // 전화번호 중복 비교
    public String phoneCheck(String phone){
        try {
            Member member = memberRepository.findByPhone(phone);
            if(member != null){
                return "no";
            }
            return "yes";
        }catch (Exception e){
            List<Member> member = memberRepository.findByPhoneMember(phone);
            if(member != null){
                return "no";
            }
            return "yes";
        }
    }
}
