document.querySelector(".signup-btn").addEventListener("click", function () {
    let username = document.querySelector(".username");
    let password = document.querySelector(".password");
    let email = document.querySelector(".email");
    let nickname = document.querySelector(".nickname");

    let url = "http://localhost:8080/api/auth/signup";

    let data = {
        username: username.value,
        password: password.value,
        email: email.value,
        nickname: nickname.value,
        role : "user"
    };
    console.log(data);
    let isValid = validate(data);

    if (isValid) {
        axios.post(url, data).then(function () {
                window.location.href = "/login";
        }).catch(function() {
            alert("회원가입에 실패했습니다. 다시 시도해주세요");
        });
    }


});

function validate(data) {
    const ID_CHECK = /^[a-z0-9]{4,10}$/;
    const EMAIL_CHECK = /^([a-z0-9]+)@([\da-z\.-]+)\.([a-z\.]{1,50})$/;
    const NICKNAME_CHECK = /^[a-zA-Z0-9/가-힣]{2,30}$/;
    let isValid = false;
    if (!ID_CHECK.test(data.username) || data.username == "") {
        console.log(data.username);
        alert("아이디는 소문자 4~10자로 구성해주세요");
    } else if(data.password == "") {
        alert("패스워드를 입력해주세요")
    } else if (!EMAIL_CHECK.test(data.email) || data.email == "") {
        alert("이메일 형식으로 작성해주세요")
    } else if (!NICKNAME_CHECK.test(data.nickname) || data.nickname == "") {
        alert("한글, 알파벳 대,소문자, 숫자로 구성해주세요");
    } else {
        isValid = true;
    }
    return isValid;
}

