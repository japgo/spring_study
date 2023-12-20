
let username = document.querySelector(".username");
let password = document.querySelector(".password");
let url = "http://localhost:8080/api/auth/login";

document.querySelector(".submit-btn").addEventListener("click", function() {
    let data = {username : username.value, password : password.value};

    axios.post(url, data).then(function(response) {
        console.log(response);
        if(response.status == "200") {
            window.location.href = 'http://localhost:8080/';
        }
    }).catch(function() {
        alert("일치하는 유저가 없습니다. 다시 시도해주세요 ");
        username.value = "";
        password.value = "";
    });

}, false);

//
// <script>
//     $(document).ready(function () {
//     // 토큰 삭제
//     Cookies.remove('Authorization', {path: '/'});
// });
//
//     const href = location.href;
//     const queryString = href.substring(href.indexOf("?") + 1)
//     if (queryString === 'error') {
//     const errorDiv = document.getElementById('login-failed');
//     errorDiv.style.display = 'block';
// }
//
//     const host = 'http://' + window.location.host;
//
//     function onLogin() {
//     let username = $('#username').val();
//     let password = $('#password').val();
//
//     $.ajax({
//     type: "POST",
//     url: `/api/user/login`,
//     contentType: "application/json",
//     data: JSON.stringify({username: username, password: password}),
// })
//     .done(function (res, status, xhr) {
//     const token = xhr.getResponseHeader('Authorization');
//
//     Cookies.set('Authorization', token, {path: '/'})
//
//     $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
//     jqXHR.setRequestHeader('Authorization', token);
// });
//
//     window.location.href = host;
// })
//     .fail(function (jqXHR, textStatus) {
//     alert("Login Fail");
//     window.location.href = host + '/api/user/login-page?error'
// });
// }
// </script>
