package com.yanolja.clone.lego.service;

import com.yanolja.clone.lego.entity.Admin;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.repository.AdminRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class AdminSignUpService implements UserDetailsService {
    // AdminRepository 를 사용하기 위해 final 로 미리 객체를 생성
    private final AdminRepository adminRepository;

    // UserDetailService 를 상속받아 기능 작성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username 으로 admin 객체 검색
        Admin admin = adminRepository.findById(username);
        // 만약 admin 객체가 null 이라면 오류 던지기
        if(admin == null) {
            throw new UsernameNotFoundException(username);
        }
        // User 클래스로 admin 의 id 와 비밀번호, 권한을 가진 객체로 빌드
        return User.builder()
                .username(admin.getId())
                .password(admin.getPassword())
                .roles(admin.getRollName())
                .build();

    }

    // admin 유저 회원가입
    public String signUp(Admin admin, PasswordEncoder passwordEncoder){
        // admin 객체의 비밀번호 암호화
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        // admin 객체의 권한 설정
        admin.setRollName("ADMIN");
        // admin 객체의 닉네임 설정
        admin.setNickname("관리자");
        // admin 객체를 데이터베이스에 저장
        Admin signUpAdmin = adminRepository.save(admin);
        // 데이터베이스에 저장되고 객체를 가져왔을 때 값이 제대로 저장되지 않았다면  'no' 라는 값을 리턴
        if(signUpAdmin == null){
            return "no";
        }else{
            // 값이 제대로 저장되었다면 'yes' 라는 값을 리턴
            return "yes";
        }
    }

    // admin 유저 검색
    public Admin findUser(String id){
        // 파라미터로 들어온 id 값으로 admin 유저 검색
        Admin findAdmin = adminRepository.findById(id);
        // 값이 없다면 null 값을 리턴
        if(findAdmin == null){
            return null;
        }
        // 값이 있다면 admin 객체 리턴
        return findAdmin;
    }

    // admin 아이디 중복 검사
    public String idCheck(String id){
        // 파라미터로 들어온 id 값으로 admin 유저 검색
        Admin findAdmin = adminRepository.findById(id);
        // 값이 있다면 유저가 이미 있는 것이기 때문에 회원가입을 하지 못하도록 'no' 라는 값을 리턴
        if(findAdmin != null){
            return "no";
        }
        // 값이 없다면 유저가 없는 것이기 때문에 회원가입을 할 수 있도록 'yes' 라는 값을 리턴
        return "yes";
    }
}
