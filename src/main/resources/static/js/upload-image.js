const dropArea = document.getElementById("drop-area");
const uploadedImage = document.getElementById("image");
const imageView = document.getElementById("img-view");

uploadedImage.addEventListener("change", uploadImage);

function uploadImage(){
    let imgLink = URL.createObjectURL(uploadedImage.files[0]);
    imageView.style.backgroundImage = `url(${imgLink})`;
    imageView.textContent = "";
    imageView.style.border = 0;
}

dropArea.addEventListener("dragover", function(e){
    e.preventDefault();
});

dropArea.addEventListener("drop", function(e){
   e.preventDefault();
   uploadedImage.files = e.dataTransfer.files;
   uploadImage();
});