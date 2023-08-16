package com.yanolja.clone.lego.config;

import com.yanolja.clone.lego.service.BusinessSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(value = 2)
public class BusinessSecurityConfig extends WebSecurityConfigurerAdapter {
    // BusinessSignUpService 를 사용하기 위해 AutoWired 로 연결
    @Autowired
    BusinessSignUpService businessSignUpService;

    // 비밀번호 암호화를 사용하기 위해 작성
    @Bean
    PasswordEncoder businessPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 페이지 권한 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        // '/business/' 로 들어오는 모든 url 에 권한이 "BUSINESS" 인 사용자만 들어올 수 있게 하기위해 작성
//        http.csrf()
//                .disable()
//                .requestMatchers()
//                .antMatchers("/business/**")
//                .and()
//                .authorizeRequests() // 보안 검사기능 시작
//                .antMatchers("/business/", "/business/roomAddPage/**", "/business/roomUpdatePage/**", "/business/roomDelete/**").hasRole("BUSINESS")
//                .anyRequest().permitAll();

        http.csrf()
                .disable()
                .requestMatchers()
                .antMatchers("/business/**")
                .and()
                .authorizeRequests()
                .antMatchers("/", "/n/", "/loginPage/**", "/signUpPage/**", "/business/login/**", "/business/signUpPage/**", "/admin/login/**", "/admin/signUpPage/**").permitAll()
                // '/myPage/', '/placePost/' 로 들어오는 모든 url 에 권한이 'USER' 인 사용자만 들어올 수 있게 하기위해 작성
                .antMatchers("/myPage/**", "/placePost/**", "/pay/**").hasRole("USER")
                // '/business/', '/business/roomAddPage/', '/business/roomUpdatePage/" 로 들어오는 모든 url 에 권한이 'BUSINESS' 인 사용지민 들어올 수 있게 하기위해 작성
                .antMatchers("/business/", "/business/roomAddPage/**", "/business/roomUpdatePage/**").hasRole("BUSINESS")
                // '/admin/' 으로 들어오는 모든 url 에 권한이 'ADMIN' 인 사용자만 들어올 수 있게 하기위해 작성
                .antMatchers("/admin/").hasRole("ADMIN")
                // 그 이외 모둔 url 은 모든 접속자가 접근 가능
                .anyRequest().hasRole("BUSINESS");

        // 권한이 없는 유저가 들어오면 이동할 경로
        http.exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());

        // 'BUSINESS' 권한을 가진 유저가 로그인을 할 때 사용할 경로, 실패시 이동할 경로, 성공시 이동할 경로 작성
        http.formLogin()
                .loginPage("/loginPage/")
                //.failureUrl("/loginPage/")
                .usernameParameter("id")
                .passwordParameter("password")
                .loginProcessingUrl("/business/login/")
                .defaultSuccessUrl("/business/login/loginSuccess/")
                .failureHandler(new AuthenticationFailureHandler() {
                    // 로그인 실패시 작동할 핸들러 생성
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        // 오류 확인하기
                        System.out.println("exception : " + exception.getMessage());
                        System.out.println(exception.toString());
                        // 에러 메세지 변수 생성
                        String errorMessage = null;
                        // 에러 결과에 따라 에러 메세지 지정
                        if(exception instanceof BadCredentialsException){
                            errorMessage = "아이디 또는 비밀번호가 잘못 입력 되었습니다.";
                        }else if(exception.getMessage().equalsIgnoreCase("가입승인 대기중인 사용자 입니다.")){
                            errorMessage = exception.getMessage();
                        }else {
                            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다.";
                        }
                        // 에러 메세지를 포함해서 다시 loginPage 로 이동
                        response.sendRedirect("/loginPage/?error=true&errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
                    }
                })
                .permitAll();

        // 로그아웃
        http.logout()
                .logoutUrl("/logout/")
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                // static 디렉터리의 하위 파일 목록은 인증 무시 (= 항상통과)
                .ignoring().antMatchers("/favicon.ico", "/resources/**", "/error", "/css/**", "/js/**", "/image/**", "/profileImage/**", "/businessImage/**", "/roomImage/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(businessSignUpService) // 로그인 할때 DB에 있는 사용자 계정을 조회할때 사용할 서비스
                // 인증 방식 :  Security --> SignUpService
                // 인증 저장소 : Security --> DB
                .passwordEncoder(businessPasswordEncoder()); // 암호화된 비밀번호 비교
    }
}
