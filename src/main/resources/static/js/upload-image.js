const dropArea = document.getElementById("drop-area");
const uploadedImage = document.getElementById("image");
const imageView = document.getElementById("img-view");

uploadedImage.addEventListener("change", uploadImage);

function uploadImage(){
    debugger;
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

function uploadDisplayImage(){
    const displayImage = document.getElementById("displayImage");

    if (displayImage){
        const url = displayImage.value;

        fetch(url)
            .then(res => res.blob())
            .then(blob => {
                const file = new File([blob], "File name",{ type: "image/png" })

                let imgLink = URL.createObjectURL(file);
                imageView.style.backgroundImage = `url(${imgLink})`;
                imageView.textContent = "";
                imageView.style.border = 0;
            })
    }
}

function loadImage() {
    let operation = window.location.pathname.split('/')

    if (operation.at(-2) === "edit" || operation.at(-1) === "edit"){
        uploadDisplayImage()
    }
}

window.addEventListener('load', loadImage)