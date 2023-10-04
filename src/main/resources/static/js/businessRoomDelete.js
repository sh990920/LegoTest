// 방 삭제하기
function roomDelete(){
    let idx = document.getElementById("idx").value;
    let url = "/business/roomDelete/"
    let param = "idx=" + idx;
    sendRequest(url, param, resultDelete, "GET");
}
function resultDelete(){
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        let data = xhr.responseText;

        if (data == "no") {
            alert("방 삭제 실패");
        } else {
            alert("방 삭제 성공");
            location.reload();
        }
    }
}