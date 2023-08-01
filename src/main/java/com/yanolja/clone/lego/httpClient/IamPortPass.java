package com.yanolja.clone.lego.httpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja.clone.lego.util.PortOne;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class IamPortPass {
    public static JsonNode getTokenV1() {
        final String requestUrl = PortOne.AUTHENTICATION_V1_URL;

        final String imp_key = PortOne.IMP_KEY;

        final String api_secret_key = PortOne.IMP_SECRET_KEY;

        final HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(requestUrl);
        postRequest.setHeader("Content-Type", "application/json");
        JsonNode returnJson = null;
        try {
            // json 데이터 생성
            JSONObject requestBody = new JSONObject();
            // json 데이터에 필요한 정보 넣기(api_key)
            requestBody.put("imp_key", imp_key);
            requestBody.put("imp_secret", api_secret_key);
            // json 데이터를 StringEntity 객체로 설정
            StringEntity entity = new StringEntity(requestBody.toString());
            // 생성된 객체를 외부 서버와 통신할 httpMethod 에 저장
            postRequest.setEntity(entity);
            // 생성된 httpMethod 를 httpClient 에 저장한 후 실행하고 HttpResponse 로 결과값 받아오기
            HttpResponse response = client.execute(postRequest);
            // ObjectMapper - JSON 형식의 데이터를 Java 객체로 역직렬화(Deserialize)하거나, 반대로 Java 객체를 JSON 형식의 데이터로 직렬화(Serialize)할 때 사용하는 Jackson 라이브러리의 클래스이다.
            ObjectMapper mapper = new ObjectMapper();
            // json 형식의 반환값을 위에서 만들어둔 jsonNode 객체에 파싱해서 결과값 저장
            returnJson = mapper.readTree(response.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnJson;
    }
    public static JsonNode getToken() {
        // api 를 사용하기 위해서 PortOne 인터페이스에 저장된 url 정보 가져오기
        final String requestUrl = PortOne.AUTHENTICATION_URL;
        // api 를 사용하기 위해서 PortOme 인터페이스 저장된 api_key 정보 가져오기
        final String apiKey = PortOne.API_KEY;
        // 외부 서버와 통신할 httpClient 생성하기
        HttpClient client = HttpClientBuilder.create().build();
        // 외부 서버와 통신할 때 사용할 HttpMethod 를 생성
        HttpPost postRequest = new HttpPost(requestUrl);
        // 외부 서버와 통신할 때 사용할 Header 설정
        postRequest.setHeader("Content-Type", "application/json");
        // 결과값을 저장할 jsonNode 객체를 미리 생성
        JsonNode returnJson = null;
        try {
            // json 데이터 생성
            JSONObject requestBody = new JSONObject();
            // json 데이터에 필요한 정보 넣기(api_key)
            requestBody.put("api_key", apiKey);
            // json 데이터를 StringEntity 객체로 설정
            StringEntity entity = new StringEntity(requestBody.toString());
            // 생성된 객체를 외부 서버와 통신할 httpMethod 에 저장
            postRequest.setEntity(entity);
            // 생성된 httpMethod 를 httpClient 에 저장한 후 실행하고 HttpResponse 로 결과값 받아오기
            HttpResponse response = client.execute(postRequest);
            // ObjectMapper - JSON 형식의 데이터를 Java 객체로 역직렬화(Deserialize)하거나, 반대로 Java 객체를 JSON 형식의 데이터로 직렬화(Serialize)할 때 사용하는 Jackson 라이브러리의 클래스이다.
            ObjectMapper mapper = new ObjectMapper();
            // json 형식의 반환값을 위에서 만들어둔 jsonNode 객체에 파싱해서 결과값 저장
            returnJson = mapper.readTree(response.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 저장된 결과값 반환
        return returnJson;
    }

    public static JsonNode getResult(String paymentId, String accessToken){
        // api 를 사용하기 위해서 PortOne 인터페이스에 저장된 url 정보를 가져온 후 파라미터로 받은 paymentId 를 url 뒤에 붙여서 사용
        final String requestUrl = PortOne.PAYMENT_DETAILS_URL + paymentId;
        // 외부 서버와 통신할 httpClient 생성하기
        HttpClient client = HttpClientBuilder.create().build();
        // 외부 서버와 통신할 때 사용할 HttpMethod 를 생성
        HttpGet getRequest = new HttpGet(requestUrl);
        // 외부 서버와 통신할 때 사용할 Header 설정
        getRequest.addHeader("Authorization", "Bearer " + accessToken);
        // 결과값을 저장할 jsonNode 객체를 미리 생성
        JsonNode returnJson = null;
        try {
            // 생성된 httpMethod 를 httpClient 에 저장한 후 실행하고 HttpResponse 로 결과값 받아오기
            final HttpResponse response = client.execute(getRequest);
            // ObjectMapper - JSON 형식의 데이터를 Java 객체로 역직렬화(Deserialize)하거나, 반대로 Java 객체를 JSON 형식의 데이터로 직렬화(Serialize)할 때 사용하는 Jackson 라이브러리의 클래스이다.
            ObjectMapper mapper = new ObjectMapper();
            // json 형식의 반환값을 위에서 만들어둔 jsonNode 객체에 파싱해서 결과값 저장
            returnJson = mapper.readTree(response.getEntity().getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 저장된 결과값 반환
        return returnJson;
    }

    public static JsonNode getUserInfo(String impUid, String accessToken) { // 19. 파라미터로 컨트롤러에서 넘어온 imp_uid와 accessToken을 받아온다.
        final String RequestUrl = PortOne.CERTIFICATION_URL + impUid;

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet get = new HttpGet(RequestUrl);

        // 23. 해당 메소드의 반환 값으로 사용할 변수를 미리 만들어둔다.
        JsonNode returnJson = null;

        try {
            get.addHeader("Authorization", "Bearer " + accessToken);
            final HttpResponse response = client.execute(get);
            ObjectMapper mapper = new ObjectMapper();
            returnJson = mapper.readTree(response.getEntity().getContent());

        } catch (UnsupportedEncodingException e) { // 지원되지 않는 인코딩 예외
            e.printStackTrace();
        } catch (ClientProtocolException e) { // 클라이언트 프로토콜 예외
            e.printStackTrace();
        } catch (IOException e) { // I/O 예외
            e.printStackTrace();
        }

        return returnJson;
    }



}
