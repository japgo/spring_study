document.addEventListener("DOMContentLoaded", function() {
  let url = "http://localhost:8080/api/posts";
  axios.get(url).then(function(response) {
    let data = response.data.responseBody.data;
    makeTemplate(data);
  }).catch(e => console.error(e));
})



function makeTemplate(data) {
  console.log(data);
  let template = document.getElementById("content-template").innerText;

  let bindTemplate = Handlebars.compile(template);

  let resultHtml = data.reduce(function(prve, next) {
    return prve + bindTemplate(next);
  }, "");
  let body = document.querySelector(".content-section .inner");
  body.innerHTML = resultHtml;

}