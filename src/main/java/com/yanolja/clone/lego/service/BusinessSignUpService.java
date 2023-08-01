package com.yanolja.clone.lego.service;

import com.yanolja.clone.lego.entity.Admin;
import com.yanolja.clone.lego.entity.Business;
import com.yanolja.clone.lego.entity.Member;
import com.yanolja.clone.lego.repository.BusinessRepository;
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
public class BusinessSignUpService implements UserDetailsService {
    // BusinessRepository 를 사용하기 위해 final 로 미리 객체를 생성
    private final BusinessRepository businessRepository;

    // UserDetailService 를 상속받아 기능 작성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username 으로 business 객체 검색
        Business business = businessRepository.findById(username);
        // 만약 business 객체가 null 이라면 오류 던지기
        if(business == null) {
            throw new UsernameNotFoundException(username);
        }
        // User 클래스로 business 의 id 와 비밀번호, 권한을 가진 객체로 빌드
        return User.builder()
                .username(business.getId())
                .password(business.getPassword())
                .roles(business.getRollName())
                .build();

    }

    // business 유저 회원가입
    public String signUp(Business business, PasswordEncoder passwordEncoder){
        // business 객체의 비밀번호 암호화
        business.setPassword(passwordEncoder.encode(business.getPassword()));
        // business 객체의 회원승인을 2로 설정(나중에 관리자가 승인하도록 코드 수정 예정)
        business.setAuthentication(2);
        // business 객체의 권한 설정
        business.setRollName("BUSINESS");
        // business 객체를 데이터베이스에 저장
        Business signUpBusiness = businessRepository.save(business);
        // 데이터베이스에 저장되고 객체를 가져왔을 때 값이 제대로 저장되지 않았다면 'no' 라는 값을 리턴
        if(signUpBusiness == null){
            return "no";
        }else{
            // 값이 제대로 저장되었다면 'yes' 라는 값을 리턴
            return "yes";
        }
    }

    // business 유저 검색
    public Business findUser(String id){
        // 파라미터로 들어온 id 값으로 business 유저 검색
        Business findBusiness = businessRepository.findById(id);
        // 값이 없다면 null 값을 리턴
        if(findBusiness == null){
            return null;
        }
        // 깂이 있다면 business 객체 리턴
        return findBusiness;
    }

    // business 아이디 중복 검사
    public String idCheck(String id){
        // 파라미터로 들어온 id 값으로 business 유저 검색
        Business findBusiness = businessRepository.findById(id);
        // 값이 있다면 유저가 이미 있는 것이기 때문에 회원가입을 하지 못하도록 'no' 라는 값을 리턴
        if(findBusiness != null){
            return "no";
        }
        // 값이 없다면 유저가 없는 것이기 때문에 회원가입을 할 수 있도록 'yes' 라는 값을 리턴
        return "yes";
    }

    // business 장소 이름 중복 검사
    public String placeNameCheck(String placeName){
        Business findBusiness = businessRepository.findByPlaceName(placeName);
        if(findBusiness != null){
            return "no";
        }
        return "yes";
    }

    public String placeCallCheck(String placeCall){
        Business findBusiness = businessRepository.findByPlaceCall(placeCall);
        if(findBusiness != null){
            return "no";
        }
        return "yes";
    }

}
