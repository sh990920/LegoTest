// 비밀번호 체크변수
let checkPassword = false;

// 비밀번호 유효성 검사
function passwordCheck1() {
    let password = document.getElementById("password").value;
    let passwordError1 = document.getElementById("passwordError1");

    let pattern1 = /[0-9]/; // 숫자 입력
    let pattern2 = /[a-zA-Z]/; // 영어 소문자, 대문자 입력
    let pattern3 = /[~!@#$%^&*()_+]/; // 특수기호 입력

    if ( !pattern1.test(password) || !pattern2.test(password) || !pattern3.test(password) || password.length < 5 ) {
        passwordError1.innerHTML = "영문, 숫자, 특수기호(~!@#$%^&*()_+)를 포함하여 5자리 이상으로 구성해야 합니다";
    } else {
        passwordError1.innerHTML = "";
    }
}

// 비밀번호 일치 검사
function passwordCheck2(){
    let password1 = document.getElementById("password").value;
    let password2 = document.getElementById("password2").value;
    let passwordError2 = document.getElementById("passwordError2");
    if(password1 == password2){
        passwordError2.innerHTML = "";
        checkPassword = true;
    }else{
        passwordError2.innerHTML = "비밀번호가 일치하지 않습니다";
        checkPassword = false;
    }
}