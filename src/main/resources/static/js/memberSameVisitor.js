function memberSameVisitor(){
    let check = document.getElementById("check");
    let memberName = document.getElementById("memberName");
    let memberPhone = document.getElementById("memberPhone");
    let visitorName = document.getElementById("visitorName");
    let visitorPhone = document.getElementById("visitorPhone");
    if(check.checked){
        visitorName.value = memberName.value;
        visitorName.readOnly = true;
        visitorPhone.value = memberPhone.value;
        visitorPhone.readOnly = true;
    }else{
        visitorName.value = "";
        visitorName.readOnly = false;
        visitorPhone.value = "";
        visitorPhone.readOnly = false;
    }
}