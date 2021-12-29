# R-SUPPORT 백엔드 개발자 과제전형

## 목차
- 0] 개발환경 && 사용기술
- 1] 대용량 트래픽 고려 [Link](https://github.com/d-h-k/bulletin-board#1-%EB%8C%80%EC%9A%A9%EB%9F%89-%ED%8A%B8%EB%9E%98%ED%94%BD-%EA%B3%A0%EB%A0%A4)
- 2] 첨부파일 기능 [Link](https://github.com/d-h-k/bulletin-board#2-%EC%B2%A8%EB%B6%80%ED%8C%8C%EC%9D%BC-%EA%B8%B0%EB%8A%A5)
- 3] 실행방법 [Link](https://github.com/d-h-k/bulletin-board#3-%EC%8B%A4%ED%96%89%EB%B0%A9%EB%B2%95)

<br>
<br>
<br>
<br>

## 0) 개발환경 && 사용기술
- IDE : IntelliJ
- Build & Package : Gradle
- Language : Java (JDK 11)
  - DB Access : JPA (Hibernate)
- DB : H2 (in-memory)

<br>
<br>
<br>
<br>

## 1) 대용량 트래픽 고려
- 대용량 트래픽이라는 요구사항을 듣고 `서버 보틀넥 제거`, `스케일 아웃` 두가지가 방식이 떠올랐는데, 여기서는 `RDB 에서의 보틀넥인 조회수 증가` 를 해결했습니다

### 조회수 증가가 보틀넥인 이유 
- 상황 : 단시간에 트래픽이 몰리는 상황이라면
  - 1초에 1만번씩 특정 게시글에 get 요청이 들어오면, 조회수 단 하나의 필드값 증가를 위해 DB에 select문을 1초만에 1만번, 값이 1씩 변하는 insert 문을 DB에 보내줘야 합니다
  - DB 가 아닌 좀더 가볍고 빠른 저장소를 사용해 조회시마다 매번 insert 문을 내보내지 않고, 1초마다 한번씩만 조회수 변화를 반영하도록 제작하였습니다 
    - DB Insert 횟수가 줄어들고 (RDB의 삽입동작 시간복잡도는 log-N 이기 때문에) 
  - `@readOnly` 사용으로 트랜잭션 종료시 flush 를 사용하지 않고, 더티체킹을 위한 스냅샷 비교를 하지 않아 성능이 개선됩니다

## 구현하기까지의 과정
- 처음에는 Redis를 사용해서 위 기능을 구현하려 하였으나, 구현에 실패했습니다
- 이를 대체하기위해 collection framework 중에서 HashMap을 사용해 해당 기능을 구현하였습니다
- Key-Value 구조를 사용해 성능 최적화하기 위한 구조는 아래 그림과 같습니다
- ![설명](https://user-images.githubusercontent.com/31065684/147671915-9f4846bc-b545-47dd-99c3-47a1fe273734.png)
- 위 버퍼에서 1초동안 조회횟수를 count 하고, 1초 이후 카운트에 반영해서 아무리 많이 요청되도 조회수 증가에 의한 쿼리는 PK 별로 1초에 한번만 insert문이 나가게 됩니다
- 이 로직을 `PostViewCountService` 클래스에 구현했고, `PostViewCountServiceTest` 클래스에서 테스트코드를 작성했습니다


<br>
<br>
<br>
<br>


## 2) 첨부파일 기능

- 첨부파일 기능 구현을 위해서 아래의 구조를 생각했습니다
- ![대락사진](https://user-images.githubusercontent.com/31065684/147674279-a81788da-8c37-4902-98e9-6768025fa99a.png)
- 엔티티패키지 `attachFile`
- storage 저장 기능을 `attachFile` 패키지에서 구현하지 않은 이유는, 게시판 특성상 미래에 다른 요구사항, 예를들어 본문에 포함된 사진을 자동으로 저장하는 기능을 대비하기 위해서, `메타데이터` 정보와 `실제 파일을 전송하는`
두가지 계층으로 분할해서 구현했습니다.
- 추후 클라이언트와 파일을 주고받을 다른 모듈에서도 `FileService` 를 사용하면 검증된 코드를 적은 작업으로 사용할수 있어 DRY 하게 구성하였습니다
- 뿐만 아니라, 추후 저장방식을 FileSystem이 아닌 AWS S3 같은 클라우드 저장소로 이동시에도 대응할수 있도록 FileServie 를 인터페이스로 분리해두었플므로  DIP 를 준수하려고 노력했습니다.


## 3) 실행방법
- DB 셋팅은 별도로 존재하지 않습니다. gradle로 실행하며
```shell
$ ./gradlew bootrun
```
- 프로젝트 디렉토리에서 위 명령어로 실행이 가능합니다
- 서버 동작시 API 문서 페이지 : http://localhost:8080/swagger-ui/
![스웨거는살아있다](https://user-images.githubusercontent.com/31065684/147682354-d2d5a769-3c96-4672-b799-d8269070616a.png)
