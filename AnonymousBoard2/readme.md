# 스프링 익명 게시판 프로젝트
* 스프링으로 익명 게시판을 만들 수 있다.
* 데이터베이스는 MongoDB를 사용해 보자.
***


## Use Case
![img](https://github.com/japgo/spring_study/blob/master/AnonymousBoard2/img/%EA%B2%8C%EC%8B%9C%ED%8C%90%20use%20case.drawio.png)
***


## API
|기능|Method|URL|REQUEST|RESPONSE|
|:-:|:----:|---|-------|--------|
|게시판 전체 보기|GET|/api/board| |게시글 전체 목록|
|개별 개시글 보기|GET|/api/board/{id}| |{ <br> "title":string, <br> "body":string <br> "lastModifiedDate":LocalDateTime <br> }|
|게시글 작성|POST|/api/board|title, body|작성 성공 여부|
|게시글 삭제|DELETE|/api/board/{id}| |삭제 성공 여부|
|게시글 수정|PATCH|/api/board/{id}|title, body|수정 성공 여부|


## ERD
* post collection에 아래 내용으로 데이터 관리

![img](https://github.com/japgo/spring_study/blob/master/AnonymousBoard2/img/%E1%84%8B%E1%85%B5%E1%86%A8%E1%84%86%E1%85%A7%E1%86%BC%20%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB.png)
***
