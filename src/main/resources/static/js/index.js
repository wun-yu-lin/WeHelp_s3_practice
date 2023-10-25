index_main();
index_async_main();

function index_main() {
    formSubmitUploader()
}

async function index_async_main() {
    let dataArr = await fetch_data()
    insertImgByArrData(dataArr)
    pictureDeleter()
}

function insertImgByArrData(dataArr) {
    document.querySelector(".imgContainer").innerHTML = ""
    if (dataArr.length === 0) {
        return null;
    }
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
        let button = document.createElement("button")
        button.className = "deleteButton"
        button.innerText = "刪除相片"
        button.value = data.key
        button.type = "submit"
        button.onclick = pictureDeleter


        divItemDiv.appendChild(imgP)
        divItemDiv.appendChild(img)
        imgContainerDiv.appendChild(divItemDiv)
        imgContainerDiv.appendChild(button)
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

    try {
        let response = await fetch(
            parameterDic.url,
            {
                method: parameterDic.method,
                headers: parameterDic.headers,
            })
        return await response.json()

    }catch (e) {
        console.log(e.message)
        return null
    }



}

//處理表單發送的請求
function formSubmitUploader() {
    let form = document.getElementById("pictureUploadForm");
    form.onsubmit = async function (event) {
        event.preventDefault();
        let currentForm = event.currentTarget;
        let url = form.action;
        try {
            let formData = new FormData(currentForm);
            let response = await fetch(url, {
                method: "POST",
                body: formData
            })
            if (response.status !== 200) {
                alert("上傳失敗")
            }
            alert("上傳成功")
            location.reload()
        } catch (e) {
            alert(e.message)
        }
    }
}

async function pictureDeleter(event) {

    console.log("onclick")
    event.preventDefault();
    let key = event.currentTarget.value
    let url = "./api/picture/delete?key=" + key

    let response = await fetch(url, {method: "DELETE"})
    if (response.status !== 200) {
        alert("刪除失敗")
        window.location.reload()
    }
    let dataArr = await fetch_data()
    insertImgByArrData(dataArr)
}



