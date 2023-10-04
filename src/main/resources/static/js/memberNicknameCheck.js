// 닉네임 체크
let checkNickname = false;
// 닉네임 중복 검사
function nicknameCheck(){
    let nickname = document.getElementById("nickname").value;
    if(nickname == ""){
        alert("닉네임을 입력해주세요.");
        return;
    }
    let url = "/signUpPage/signUp/nicknameCheck";
    let param = "nickname=" + nickname;
    sendRequest(url, param, resultNicknameCheck, "GET");
}
function resultNicknameCheck(){
    let nickname = document.getElementById("nickname");
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        let data = xhr.responseText;

        if (data == "no") {
            alert("닉네임이 중복 됩니다.\n 다시 입력해주세요.");
            nickname.value = "";
        } else {
            alert("사용 가능한 닉네임 입니다.\n 다음 순서를 진행해주세요.");
            checkNickname = true;
            nickname.readOnly=true;
        }
    }
}