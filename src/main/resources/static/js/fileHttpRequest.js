let xhr = null;

function createRequest(){
    //JavaScript를 이용하여 서버로 정보를 보내는 HTTP request를 만들기 위해서는 이런 기능을 제공하는 클래스 인스턴스가 필요하다.
    //이런 클래스는 InternetExplorer에서는 XMLHTTP라고 불리는 ActivX object를 말한다.
    //그러면 Mozzlia, Safari나 다른 브라우저에서도 Microsoft의 ActiveX 객체의 method와 property를 지원하기 위해 XMLHttpRequest클래스를 구현 하고 있다.
    if(xhr != null){
        return;
    }
    if(window.ActiveXObject){
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }else{
        xhr = new XMLHttpRequest();
    }
}

function sendRequest(url, param, callBack, method, elementId){
    createRequest();

    let httpMethod = null;

    if(method != 'POST' && method != 'post'){
        httpMethod = 'GET';
    }else{
        httpMethod = 'POST';
    }

    let httpParam = null;
    if(param == null || param == ''){
        httpParam = null;
    }else{
        httpParam = param;
    }

    let httpURL = url;

    let fileInput = document.getElementById(elementId);
    let file = fileInput.files[0];
    let formData = new FormData();
    formData.append(elementId, file);



    if(httpMethod == 'GET' && httpParam != null){
        httpURL = httpURL + "?" + httpParam;
    }

    xhr.open(httpMethod, httpURL, true);

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    xhr.onreadystatechange = callBack;

    if(httpMethod == 'POST'){
        formData.append(httpParam[0], httpParam[1]);
        xhr.send(formData);
    }else{
        xhr.send(formData);
    }


}