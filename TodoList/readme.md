# Todo List 프로젝트
* 회원 가입, 로그인 기능 구현
* Todo 완료 기능
* 댓글 작성, 수정, 삭제 기능
* ( 예외 처리 )
***


## Use Case
![img](https://github.com/japgo/spring_study/blob/master/AnonymousBoard2/img/%EA%B2%8C%EC%8B%9C%ED%8C%90%20use%20case.drawio.png)
***

## API
|     기능     | Method | URL             | REQUEST HEADER | REQUEST                                                                   | RESPONSE                                                                                                                                                                                                                                                                     | RESPONSE HEADER |
|:----------:|:------:|-----------------|----------------|---------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
|   전체 보기    |  GET   | /api/board      |                |                                                                           | 전체 목록                                                                                                                                                                                                                                                                        |                 |
|  Todo 작성   |  POST  | /api/board      | 쿠키 정보          | { <br/> "title":string,<br/> "content":string<br/>}                       | 작성된 todo                                                                                                                                                                                                                                                                     |                 |
| 개별 Todo 보기 |  GET   | /api/board/{id} |                |                                                                           | { <br/> "userName":string, <br/> "title":string, <br/> "content":string, <br/> "progress":string,<br/> "lastModifiedDate":LocalDateTime <br/> "comment": [ <br/> { "userName":string,<br/>"content":string,<br/>"lastModifiedDate":LocalDateTime }, <br/>... <br/> ] <br/> } |                 |
|  Todo 삭제   | DELETE | /api/board/{id} | 쿠키 정보          |                                                                           | { <br/> "msg":string,<br/>"statusCode":int<br/>}                                                                                                                                                                                                                             |                 |
|  Todo 수정   | PATCH  | /api/board/{id} | 쿠키 정보          | { <br/> "title":string,<br/>"content":string,<br/>"progress":string<br/>} | 수정된 todo                                                                                                                                                                                                                                                                     |                 |
|   회원 가입    |  POST  | /api/member     |                |                                                                           | { <br/> "msg":string,<br/>"statusCode":int<br/>}                                                                                                                                                                                                                             |                 |
|    로그인     |  POST  | /api/auth/login |                |                                                                           | { <br/>"msg":string,<br/>"statusCode":int<br/>}                                                                                                                                                                                                                              | 쿠키 정보           |


## ERD
* todo collection에 아래 내용으로 데이터 관리

![img](https://github.com/japgo/spring_study/blob/master/AnonymousBoard2/img/%EC%9D%B5%EB%AA%85%20%EA%B2%8C%EC%8B%9C%ED%8C%90.png)
***
