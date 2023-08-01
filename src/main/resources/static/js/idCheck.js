// 아이디 체크
let checkId = false;
// 아이디 중복 검사
function idCheck(inputUrl){
    let id = document.getElementById("id").value;
    if(id == ""){
        alert("아이디를 입력해주세요.");
        return;
    }
    let url = inputUrl;
    let param = "id=" + id;
    sendRequest(url, param, resultIdCheck, "GET");
}
function resultIdCheck(){
    let id = document.getElementById("id");
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        let data = xhr.responseText;

        if (data == "no") {
            alert("아이디가 중복 됩니다.\n 다시 입력해주세요.");
        } else {
            alert("사용 가능한 아이디 입니다.\n 다음 순서를 진행해주세요.");
            checkId = true;
            id.readOnly=true;
        }
    }
}