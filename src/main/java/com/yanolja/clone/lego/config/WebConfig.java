package com.yanolja.clone.lego.config;

import com.yanolja.clone.lego.util.Path;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String connectProfilePath = "/profileImage/**"; // 리소스와 연결될 URL path를 지정한다. (클라이언트가 파일에 접근하기 위해 요청하는 url)
    // 실제 리소스가 존재하는 외부 경로를 지정한다.
    // 경로의 마지막은 반드시 " / "로 끝나야 하고, 로컬 디스크 경로일 경우 file:/// 접두어를 꼭 붙여야 한다.
    //private String profileImage = "file:///Users/seunghwan/Desktop/Lego/image/profile/";
    private String profileImage = "file://" + Path.PROFILE_IMAGE_PATH + "/";

    // 사업자 이미지 경로
    private String connectBusinessPath = "/businessImage/**";
    private String businessImage = "file://" + Path.BUSINESS_IMAGE_PATH + "/";

    // 룸 이미지 경로
    private String connectRoomPath = "/roomImage/**";
    private String roomImage = "file://" + Path.ROOM_IMAGE_PATH + "/";

    // 페이지 꾸미기 용도 이미지 경로
    private String connectImagePath = "/image/**";
    private String image = "file://" + Path.IMAGE_PATH + "/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectProfilePath) // 리소스와 연결될 URL path를 지정한다. (클라이언트가 파일에 접근하기 위해 요청하는 url)
                // 실제 리소스가 존재하는 외부 경로를 지정한다.
                // 경로의 마지막은 반드시 " / "로 끝나야 하고, 로컬 디스크 경로일 경우 file:/// 접두어를 꼭 붙여야 한다.
                .addResourceLocations(profileImage);

        registry.addResourceHandler(connectBusinessPath)
                .addResourceLocations(businessImage);

        registry.addResourceHandler(connectRoomPath)
                .addResourceLocations(roomImage);

        registry.addResourceHandler(connectImagePath)
                .addResourceLocations(image);
    }
}
