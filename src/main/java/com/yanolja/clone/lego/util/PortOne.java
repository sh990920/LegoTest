package com.yanolja.clone.lego.util;

public interface PortOne {
    // 포트원 access_token 을 얻기위한 api 주소(신버전)
    String AUTHENTICATION_URL = "https://api.portone.io/v2/signin/api-key";

    // 포트원 access_token 을 얻기위한 api 주소(구버전)
    String AUTHENTICATION_V1_URL = "https://api.iamport.kr/users/getToken";

    // 포트원 api_key(신버전)
    String API_KEY = "9qunjStzch8EZyi0kKUnFZhu75fXqq4l5gugsth7ck523S0WmrvP3nJtqSbDtERsWJdJBOpjTzXUX0HX";

    // 포트원 결제 단건 조회를 위한 api 주소
    String PAYMENT_DETAILS_URL = "https://api.portone.io/v2/payments/";

    // 포트원 api_key(구버전)
    String IMP_KEY = "4623303517205650";

    // 포트원 api_secret_key(구버전)
    String IMP_SECRET_KEY = "zEQ5TD4l2QBtVfQguKSAdvadiKuLjYwzibBRi3cKCyRD6ymFNbRCmBbAv9Lu4LmhIuyPC9WNw30fh7i0";

    // 포트원 본인 인증 api 주소
    String CERTIFICATION_URL = "https://api.iamport.kr/certifications/";
}
