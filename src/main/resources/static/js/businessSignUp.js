// 회원가입 기능
function signUp(){
    let id = document.getElementById("id").value;
    let password = document.getElementById("password").value;
    let name = document.getElementById("name").value;
    let placeCall = document.getElementById("placeCall").value;
    let placeName = document.getElementById("placeName").value;
    let placeAddress = document.getElementById("placeAddress").value;
    let category = document.getElementById("category").value;
    let parkingOption = document.getElementById("parkingOption").value;
    let smokingOption = document.getElementById("smokingOption").value;
    let wifiOption = document.getElementById("wifiOption").value;
    let ottOption = document.getElementById("ottOption").value;
    if(id == ""){
        alert("아이디를 입력해주세요.");
        return;
    }
    if(checkId != true){
        alert("아이디 중복 검사를 먼저해주세요.");
        return;
    }
    if(checkPassword != true){
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }
    if(placeCall == ""){
        alert("전화번호를 입력해주세요.");
        return;
    }
    if(placeName == ""){
        alert("숙박업체 이름을 입력해주세요.");
        return;
    }
    if(checkPlaceName != true){
        alert("숙박업체 이름 중복 검사를 먼저해주세요.");
        return;
    }
    if(name == ""){
        alert("사업자 성함을 입력해주세요.");
        return;
    }
    if(placeAddress == ""){
        alert("숙박업체 주소를 입력해주세요.");
        return;
    }
    if(category == ""){
        alert("카테고리를 선택해주세요.");
        return;
    }
    if(parkingOption == ""){
        alert("주차 옵션을 선택해주세요.");
        return;
    }
    if(smokingOption == ""){
        alert("흡연 옵션을 선택해주세요.");
        return;
    }
    if(wifiOption == ""){
        alert("와이파이 옵션을 선택해주세요.");
        return;
    }
    if(ottOption == ""){
        alert("TV 서비스 옵션을 선택해주세요.");
        return;
    }
    let url = "/business/signUpPage/signUp";
    let param = "id=" + id +
                "&password=" + password +
                "&name=" + name +
                "&placeCall=" + placeCall +
                "&placeName=" + placeName +
                "&placeAddress=" + placeAddress +
                "&category=" + category +
                "&parkingOption=" + parkingOption +
                "&smokingOption=" + smokingOption +
                "&wifiOption=" + wifiOption +
                "&ottOption=" + ottOption;
    sendRequest(url, param, resultSignUp, "POST");
}
function resultSignUp(){
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        let data = xhr.responseText;

        if (data == "no") {
            alert("이미 가입된 회원정보가 있습니다.\n로그인 혹은 아이디/비밀번호 찾기를 이용해 주세요");
            location.href = "/loginPage/";
        } else {
            alert("가입이 완료되었습니다.\n 로그인 페이지로 이동합니다.");
            location.href = "/loginPage/";
        }
    }
}