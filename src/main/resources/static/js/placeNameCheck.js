// 업체 이름 체크
let checkPlaceName = false;
// 업체 이름 중복 검사
function placeNameCheck(){
    let placeName = document.getElementById("placeName").value;
    if(placeName == ""){
        alert("업체 이름을 입력해주세요.");
        return;
    }
    let url = "/business/signUpPage/placeNameCheck";
    let param = "placeName=" + placeName;
    sendRequest(url, param, resultPlaceNameCheck, "GET");
}
function resultPlaceNameCheck(){
    let placeName = document.getElementById("placeName");
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        let data = xhr.responseText;

        if (data == "no") {
            alert("업체 이름이 중복 됩니다.\n 다시 입력해주세요.");
        } else {
            alert("등록 가능한 업체 입니다.\n 다음 순서를 진행해주세요.");
            checkPlaceName = true;
            placeName.readOnly=true;
        }
    }
}