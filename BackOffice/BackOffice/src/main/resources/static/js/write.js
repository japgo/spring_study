
document.querySelector(".btn").addEventListener("click", function() {
    const boardTitle = document.querySelector(".board-title").value;
    const boardContent = document.querySelector(".board-content").value;
    let url = `http://localhost:8080/api/posts`;
    let data = {title: boardTitle, content: boardContent};

    axios.post(url, data).then(function() {
        window.location.href = 'http://localhost:8080/board';
    }).catch(e => console.error(e));

});
