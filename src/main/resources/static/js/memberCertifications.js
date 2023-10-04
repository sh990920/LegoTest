let certificationName = "";
let certificationPhone = "";
function memberCertifications(){
    var IMP = window.IMP; // init로 객체초기화 (가맹점식별코드)
    IMP.init("imp88140833"); // 가맹점 번호
    IMP.certification({
        popup: true // 팝업 사용
    }, function (rsp) { // 1. 파라미터로 rsp를 받아온다.
        if ( rsp.success ){ // 인증에 성공할 경우
            // 2. 1에서 파라미터로 받아온 rsp에서 imp_uid를 가져온다.
            let impUid = rsp.imp_uid;
            //alert(impUid);
            // 3. Ajax를 사용하여 2에서 가져온 imp_uid를 컨트롤러로 보낸다.
            let url = "/signUpPage/signUp/certifications/";
            let param = "impUid=" + impUid;
            sendRequest(url, param, resultCertifications, "POST");
            return;
        } else { //인증 실패 시
            alert("인증 실패하였습니다.");
            return;
        }
    });
}
// 휴대폰 인증 콜백 메소드
function resultCertifications () {
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        // 32. 컨트롤러에서 반환된 유저 정보 Map을 받아온다.
        let data = xhr.responseText;
        // 33. 32에서 받아온 Map을 JavaScript 객체로 변환한다.
        let jsData = JSON.parse(data);
        let name = document.getElementById("name"); // 이름
        let phone = document.getElementById("phone"); // 휴대폰 번호

        // 34. 33에서 변환된 Map에 들어있는 값들을 꺼내서 각 작성란에 맞게 전달하고 변경하지 못하도록 막는다.
        name.value = jsData.name; // 변환된 Map 값 중 이름을 가져와 이름 작성란에 전달한다.
        name.readOnly = true; // 전달된 이름을 변경하지 못하도록 막는다.
        phone.value = jsData.phone; // 변환된 Map 값 중 휴대폰 번호를 가져와 휴대폰 번호 작성란에 전달한다.
        phone.readOnly = true; // 전달된 휴대폰 번호를 변경하지 못하도록 막는다.
        certificationName = jsData.name;
        certificationPhone = jsData.phone;
        // 35. Ajax를 사용하여 33에서 변환된 Map 값 중 휴대폰 번호를 컨트롤러로 보낸다. - 중복 가입자인지 체크
        let url = "/signUpPage/signUp/phoneCheck";
        let param = "phone=" + jsData.phone;
        sendRequest(url, param, resultCheckPhone, "POST");
    }
}
// 휴대폰 번호로 중복 가입자 체크 콜백 메소드
function resultCheckPhone() {
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        // 42. 컨트롤러에서 반환된 값을 받아온다.
        let data = xhr.responseText;
        let name = document.getElementById("name"); // 이름
        let placeCall = document.getElementById("phone"); // 휴대폰 번호

        // 43. 42에서 받아온 값을 체크한다.
        // 43-1. 받아온 값이 no일 경우 - 중복 가입자
        if( data == "no" ) {
            alert("이미 가입된 회원정보가 있습니다.\n로그인 혹은 아이디/비밀번호 찾기를 이용해 주세요");
            name.value = ""; // 작성된 이름을 초기화한다.
            phone.value = ""; // 작성된 휴대폰 번호를 초기화한다.
            certificationName = "";
            certificationPhone = "";
            return;
            // 43-2. 받아온 값이 yes일 경우 - 신규 가입자
        } else {
            alert("가입 가능한 회원 정보입니다.");
            return;
        }
    }
}