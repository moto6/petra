## 계획

### 비지니스 목표
- 공지사항 등록, 수정, 삭제, 조회 API 구현
- 공지사항 등록시 입력 항목
    - 제목, 내용, 공지 시작일시, 공지 종료일시, 첨부파일 (여러개)
- 공지사항 조회시 응답
    - 제목, 내용, 등록일시, 조회수, 작성자

### 테크
- 테스트 : 단위테스 & 통합테스트 모두 작성
- 대용량 트래픽 고려
    - 고속처리 : 몽고? 레디스?
    - 스케일아웃 : 위한 토큰방식? >> 계정까지 필요한데..
    - 비동기 큐잉 : ??
    - 최소한 읽기성능만큼은 신경쓰기
- 첨부파일 기능 : 첨부파일 S3 or 로컬머신 저장
- 기술적인 부분의 문제해결 전략, 실행 방법을 잘 정리하기
- REST API >> Spring HEATOAS 적용 (우선순위 낮음)
- 스웨거로 문서화
- 테스트전송 샘플


## 첨부파일 처리 기능

- [참고](https://velog.io/@yu-jin-song/Spring-Boot-%EA%B2%8C%EC%8B%9C%ED%8C%90-%EA%B5%AC%ED%98%84-5-%EA%B2%8C%EC%8B%9C%EA%B8%80-%EC%88%98%EC%A0%95-%EB%B0%8F-%EC%82%AD%EC%A0%9C-%EB%8B%A4%EC%A4%91-%ED%8C%8C%EC%9D%BC%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%B0%98%ED%99%98-%EB%B0%8F-%EC%A1%B0%ED%9A%8C-%EC%B2%98%EB%A6%AC-MultipartFile#-%EA%B2%8C%EC%8B%9C%EA%B8%80-%EB%AA%A9%EB%A1%9D-%EC%A1%B0%ED%9A%8C%EC%8B%9C) [링크](https://velog.io/@yu-jin-song/Spring-Boot-게시판-구현-5-게시글-수정-및-삭제-다중-파일이미지-반환-및-조회-처리-MultipartFile)



## MappedSuperclass 정리
- @MappedSuperclass : 객체의 입장에서 공통 매핑 정보가 필요할 때 사용
- 모든 엔티티에서 반복적으로 나오는 생성/수정시간,등등  공통 매핑 정보가 필요할 때, 부모 클래스에 선언하고 속성만 상속 받아서 사용하고 싶을 때 @MappedSuperclass를 사용한다.
- DB 테이블은 매핑되는 엔티티마다 다 따로 쓰고 있다 코드는 객체지향적으로 가져가되, SQL에 잘 들어맞게 저장된다.
- 대표적으로 생성자, 생성시간, 수정자, 수정시간 등 모든 엔티티에 공통으로 가져가야 하는 부분을 BaseEntity 를 정의해서 활용하면 편함


## 모르는키워드
- 템플릿 메서드 패턴
- 프록시 CGLib, 다이나믹 프록시
- 트랜잭션과 아이솔레이션 레벨


## 메타데이터 작성
- https://docs.spring.io/spring-boot/docs/current/reference/html/configuration-metadata.html
- 프로퍼티 커스텀 디파인 정의


## 디비 인덱스 공부하기
- https://helloinyong.tistory.com/296
