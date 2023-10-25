index_main();
index_async_main();

function index_main() {
    formSubmitUploader()

}

async function index_async_main() {
    let dataArr = await fetch_data()
    console.log(dataArr)
    insertImgByArrData(dataArr)
}

function insertImgByArrData(dataArr) {
    if (dataArr.length === 0) {
        return null;
    }
    document.querySelector(".imgContainer").innerHTML = ""
    dataArr.forEach((data) => {
            insertOneImgElement(data)
        }
    )

    function insertOneImgElement(data) {
        let imgContainerDiv = document.querySelector(".imgContainer")
        let divItemDiv = document.createElement("div")
        divItemDiv.className = "imgItem"
        let imgP = document.createElement("p")
        imgP.className = "pictureMessage"
        imgP.innerText = data.message
        let img = document.createElement("img")
        img.src = data.imgSrc
        let hr = document.createElement("hr")


        divItemDiv.appendChild(imgP)
        divItemDiv.appendChild(img)
        imgContainerDiv.appendChild(divItemDiv)
        imgContainerDiv.appendChild(hr)
    }

}

async function fetch_data() {
    let parameterDic = {
        "method": "GET",
        "url": "./api/picture/",
        "headers": {
            "Content-Type": "application/json",
        }
    }


    let response = await fetch(
        parameterDic.url,
        {
            method: parameterDic.method,
            headers: parameterDic.headers,
        })


    return await response.json()
}

//處理表單發送的請求
function formSubmitUploader(){
    let form = document.getElementById("pictureUploadForm");
    form.onsubmit = async function (event){
        event.preventDefault();
        let currentForm = event.currentTarget;
        let url = form.action;
        try {
            let formData = new FormData(currentForm);
            let response = await fetch(url,{
                method: "POST",
                body: formData
            })
            if (response.status !== 200) {
                alert("上傳失敗")
            }
            alert("上傳成功")
            location.reload()
        }catch (e){
            alert(e.message)
        }
    }
}

