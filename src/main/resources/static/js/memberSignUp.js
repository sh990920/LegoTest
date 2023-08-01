function signUp(){
    let id = document.getElementById("id").value;
    let password = document.getElementById("password").value;
    let nickname = document.getElementById("nickname").value;
    let phone = document.getElementById("phone").value;
    let name = document.getElementById("name").value;
    let url = "/signUpPage/signUp/";
    if(id == ""){
        alert("아이디를 입력해주세요");
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
    if(nickname === ""){
        alert("닉네임을 입력해주세요.");
        return;
    }
    if(phone == ""){
        alert("전화번호를 입력해주세요.");
        return;
    }
    if("name" == ""){
        alert("이름을 입력해주세요.");
        return;
    }
    let param = "id=" + id +
                "&password=" + password +
                "&nickname=" + nickname +
                "&phone=" + phone +
                "&name=" + name;
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