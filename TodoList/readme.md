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
|    기능     | Method | URL              | REQUEST                                                    | RESPONSE                                                                                                          |
|:---------:|:------:|------------------|------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| 게시판 전체 보기 |  GET   | /api/board       |                                                            | 게시글 전체 목록                                                                                                         |
|  게시글 작성   |  POST  | /api/board       | header { password } <br> body { userName, title, content } | 작성된 게시글                                                                                                           |
| 개별 개시글 보기 |  GET   | /api/board/{id}  |                                                            | { <br> "userName":string, <br> "title":string, <br> "content":string <br> "lastModifiedDate":LocalDateTime <br> } |
|  게시글 삭제   | DELETE | /api/board/{id}  |                                                            |                                                                                                                   |
|  게시글 수정   | PATCH  | /api/board/{id}  | header { password } <br> body { userName, title, content } | 수정된 게시글                                                                                                           |


## ERD
* todo collection에 아래 내용으로 데이터 관리

![img](https://github.com/japgo/spring_study/blob/master/AnonymousBoard2/img/%EC%9D%B5%EB%AA%85%20%EA%B2%8C%EC%8B%9C%ED%8C%90.png)
***
