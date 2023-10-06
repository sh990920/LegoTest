function roomUpdate(){
    let idx = document.getElementById("idx").value;
    let roomName = document.getElementById("roomName").value;
    let price = document.getElementById("price").value;
    let minPersonnel = document.getElementById("minPersonnel").value;
    let maxPersonnel = document.getElementById("maxPersonnel").value;
    let checkInTime = document.getElementById("checkInTime").value;
    let checkOutTime = document.getElementById("checkOutTime").value;
    let businessIdx = document.getElementById("businessIdx").value;
    if(roomName == ''){
        alert("방 이름을 작성해주세요");
        return;
    }
    if(price == ''){
        alert("가격을 설정해주세요");
        return;
    }
    if(minPersonnel < '1'){
        alert("최소 1명 이상은 숙소에 들어갈 수 있어야 합니다.");
        return;
    }
    if(maxPersonnel == null){
        alert("최대 인원을 설정해주세요");
        return;
    }
    if(checkInTime == null){
        alert("체크인 시간을 지정해주세요");
        return;
    }
    if(checkOutTime == null){
        alert("체크아웃 시간을 지정해주세요");
        return;
    }
    let url = "/business/roomUpdatePage/roomUpdate/";
    let param = "idx=" + idx +
        "&roomName=" + roomName +
        "&businessIdx=" + businessIdx +
        "&price=" + price +
        "&minPersonnel=" + minPersonnel +
        "&maxPersonnel=" + maxPersonnel +
        "&checkInTime=" + checkInTime +
        "&checkOutTime=" + checkOutTime;
    sendRequest(url, param, resultRoomUpdate, "GET");
}
function resultRoomUpdate(){
    if ( xhr.readyState == 4 && xhr.status == 200 ) {
        let data = xhr.responseText;

        if (data == "no") {
            alert("방 업데이트 실패");
        } else {
            alert("방 업데이트 성공");
            location.href = "/business/";
        }
    }
}