// 체크인
function checkIn(idx){
    let bookingIdx = idx;
    location.href="/business/checkIn/?idx=" + idx;
}
// 체크아웃
function checkOut(idx){
    let bookingIdx = idx;
    location.href="/business/checkOut/?idx=" + idx;
}
// 환불
function refund(idx){
    let bookingIdx = idx;
    location.href="/business/refund/?idx=" + idx;
}