document.getElementById("placeAddress").addEventListener("click", function() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById("placeAddress").value = data.address;
        }
    }).open();
});
document.getElementById("btnplaceAddress").addEventListener("click", function() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById("placeAddress").value = data.address;
        }
    }).open();
});