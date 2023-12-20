document.addEventListener("DOMContentLoaded", function () {

});


const urlParams = new URL(location.href).searchParams;

const postId = urlParams.get('id');
const postGetUrl = `http://localhost:8080/api/posts/${postId}`;
const commentGetUrl = `http://localhost:8080/api/posts/${postId}/comments`;

let heartEl = document.getElementById("heart");
heartEl.addEventListener("click", function () {
    let likeUrl = `http://localhost:8080/api/posts/${postId}/likes`;
    if(heartEl.classList.contains("active")) {
        axios.delete(likeUrl);
    } else {
        axios.post(likeUrl);
    }
    heartEl.classList.toggle("active");
});


let commentContainer = document.querySelector(".comment-each-container");
commentContainer.addEventListener("click", function(evt) {
    if(!(evt.target.tagName == "svg" || evt.target.tagName == "path")) return;

    let commentId = evt.target.closest(".comment-box").dataset.commentId;
    let url = `http://localhost:8080/api/posts/${postId}/comments/${commentId}/likes`;

    if(evt.target.tagName == "svg") {
        if(evt.target.classList.contains("active")) {
            axios.delete(url);
        } else {
            axios.post(url);
        }
        evt.target.classList.toggle("active");
    } else if(evt.target.tagName == "path") {
        if(evt.target.closest("svg").classList.contains("active")) {
            axios.delete(url);
        } else {
            axios.post(url);
        }
        evt.target.closest("svg").classList.toggle("active");
    }
});


sendAjax(postGetUrl);
axios.get(commentGetUrl).then(function (response) {
    let data = response.data.responseBody.data;
    makeCommentTemplate(data);
});

function sendAjax(url) {
    const oReq = new XMLHttpRequest();

    oReq.addEventListener("load", function () {
        makeTemplate(JSON.parse(oReq.responseText));
    });
    oReq.open("GET", url);
    oReq.send();
}

function makeTemplate(data) {

    const title = document.querySelector(".board-title");
    const content = document.querySelector(".board-content");

    title.innerText = data.responseBody.data.title;
    content.innerText = data.responseBody.data.content;
}

document.querySelector(".btn").addEventListener("click", function () {
    const commentPostUrl = `http://localhost:8080/api/posts/${postId}/comments`;
    const commentGetUrl = `http://localhost:8080/api/posts/${postId}/comments`;
    let commentContent = document.querySelector(".comment-box .input-content");
    let data = {content: commentContent.value};
    commentContent.value="";

    axios.post(commentPostUrl, data).then(function () {
        alert("댓글이 등록되었습니다");
        axios.get(commentGetUrl).then(function (response) {
            let data = response.data.responseBody.data;
            makeCommentTemplate(data);
        });
    }).catch(e => console.error(e));
}, false);

function makeCommentTemplate(data) {
    let template = document.getElementById("comment-template").innerText;

    let bindTemplate = Handlebars.compile(template);

    let resultHtml = data.reduce(function (prve, next) {
        return prve + bindTemplate(next);
    }, "");
    let body = document.querySelector(".comment-each-container");
    body.innerHTML = resultHtml;
}