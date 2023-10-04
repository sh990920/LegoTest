function checkAll(){
    let allCheck = document.getElementById("allCheck");
    let checkValue1 = document.getElementById("checkValue1");
    let checkValue2 = document.getElementById("checkValue2");
    let checkValue3 = document.getElementById("checkValue3");
    let checkValue4 = document.getElementById("checkValue4");
    let checkValue5 = document.getElementById("checkValue5");
    if(allCheck.checked == true){
        checkValue1.checked = true;
        checkValue2.checked = true;
        checkValue3.checked = true;
        checkValue4.checked = true;
        checkValue5.checked = true;
    }
    if(allCheck.checked == false){
        checkValue1.checked = false;
        checkValue2.checked = false;
        checkValue3.checked = false;
        checkValue4.checked = false;
        checkValue5.checked = false;
    }
}