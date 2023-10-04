// 이미지 미리보기
function previewImage() {
    const previewProfileImage = document.getElementById("previewProfileImage");
    const file = document.getElementById("imageFile").files[0];
    const reader = new FileReader();

    reader.addEventListener("load", function () {
        previewProfileImage.src = reader.result;
    }, false);

    if (file) {
        reader.readAsDataURL(file);
    }
}