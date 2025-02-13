# ScheduleWithJpa

<br/>


### 사용기술
------------------
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)  : 사용 언어 <br/> <br/>
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) : 프레임워크<br/><br/>
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white) : API 테스트 <br/><br/>
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) : 형상 관리<br/><br/>
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) : 데이터베이스<br/>
<br/>

### 스케줄 관리 API 명세서
------------------
|기능|Method|URL|request|response|상태코드|
|------|-----|---|---|---|---|
|회원 가입|Post|http://localhost:8080/auth/signup|요청 body|등록 정보|201 : Created|
|로그인|Post|http://localhost:8080/auth/login|요청 body|로그인 응답 정보|200 : OK|

<br/>

|기능|Method|URL|request|response|상태코드|
|------|-----|---|---|---|---|
|일정 생성|Post|http://localhost:8080/schedules|요청 body|등록 정보|201 : Created|
|일정 전체조회|Get|http://localhost:8080/schedules| 요청 param|전체 응답 정보|200 : OK|
|일정 조회|Get|http://localhost:8080/schedules/{id}|요청 param|단건 응답 정보|200 : OK|
|일정 수정|Put|http://localhost:8080/schedules/{id}|요청 body|수정 정보|200 : OK|
|일정 삭제|Delete|http://localhost:8080/schedules/{id}|요청 param|테스트3|200 : OK|

<br/>

|기능|Method|URL|request|response|상태코드|
|------|-----|---|---|---|---|
|댓글 생성|Post|http://localhost:8080/schedules/{scheduleId}/comments|요청 body|등록 정보|201 : Created|
|댓글 조회|Get|http://localhost:8080/schedules/{scheduleId}/comments| 요청 param|전체 댓글 응답 정보|200 : OK|
|댓글 수정|Get|http://localhost:8080/schedules/{scheduleId}/comments/{commentId}|요청 body|수정 정보|200 : OK|
|댓글 삭제|Put|http://localhost:8080/schedules/{scheduleId}/comments/{commentId}|요청 param|삭제 정보|200 : OK|

<br/>

### ERD 작성
------------------
![image](https://github.com/user-attachments/assets/fc4a558a-1a18-4dad-b35e-8d39f5f45785)

<br/><br/>


## 필수 기능 가이드

### Lv 0. API 명세 및 ERD 작성
- [ ] API 명세서 작성하기<br/>
      API명세서는 프로젝트 root(최상위) 경로의 README.md 에 작성
- [ ] ERD 작성하기<br/>
      ERD는 프로젝트 root(최상위) 경로의 README.md 에 첨부
      <br/>
- [ ] SQL 작성하기
   - [ ] 설치한 데이터베이스(Mysql)에 ERD를 따라 테이블을 생성
<br/>
### Lv 1. 일정 CRUD 
- [ ]  **일정 생성(일정 작성하기)**
    - [ ]  일정을 생성, 조회, 수정, 삭제할 수 있습니다.
- [ ]  일정은 아래 필드를 가집니다.
    - [ ]  `작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용합니다. → `3주차 JPA Auditing 참고!`
   
  <br/>

### Lv 2. 유저 CRUD 
- [ ]  유저를 생성, 조회, 수정, 삭제할 수 있습니다.
- [ ]  유저는 아래와 같은 필드를 가집니다.
    - [ ]  `유저명`, `이메일`, `작성일` , `수정일` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용합니다.
- [ ]  연관관계 구현
    - [ ]  일정은 이제 `작성 유저명` 필드 대신 `유저 고유 식별자` 필드를 가집니다.
      
  <br/>  
  
### Lv 3. 회원가입
- [ ]  유저에 `비밀번호` 필드를 추가합니다.
    - 비밀번호 암호화는 도전 기능에서 수행합니다.
       
  <br/>
         
### Lv 4. 로그인(인증)
- 키워드
    
    **인터페이스**
    
    - HttpServletRequest / HttpServletResponse : 각 HTTP 요청에서 주고받는 값들을 담고 있습니다.
- [ ]  **설명**
    - [ ]  **Cookie/Session**을 활용해 로그인 기능을 구현합니다. → `2주차 Servlet Filter 실습 참고!`
    - [ ]  필터를 활용해 인증 처리를 할 수 있습니다.
    - [ ]  `@Configuration` 을 활용해 필터를 등록할 수 있습니다.
- [ ]  **조건**
    - [ ]  `이메일`과 `비밀번호`를 활용해 로그인 기능을 구현합니다.
    - [ ]  회원가입, 로그인 요청은 인증 처리에서 제외합니다.
- [ ]  **예외처리**
    - [ ]  로그인 시 이메일과 비밀번호가 일치하지 않을 경우 HTTP Status code 401을 반환합니다.
   
  <br/>

## 도전 기능 가이드


### Lv 5. 다양한 예외처리 적용하기
- [ ]  Validation을 활용해 다양한 예외처리를 적용해 봅니다. → `1주차 Bean Validation 참고!`
- [ ]  정해진 예외처리 항목이 있는것이 아닌 프로젝트를 분석하고 예외사항을 지정해 봅니다.
    - [ ]  Ex) 할일 제목은 10글자 이내, 유저명은 4글자 이내
    - [ ]  `@Pattern`을 사용해서 회원 가입 Email 데이터 검증 등
        - [ ]  정규표현식을 적용하되, 정규표현식을 어떻게 쓰는지 몰두하지 말 것!
        - [ ]  검색해서 나오는 것을 적용하는 것으로 충분!
  <br/>     
### Lv 6. 비밀번호 암호화

- [ ]  Lv.3에서 추가한 `비밀번호` 필드에 들어가는 비밀번호를 암호화합니다.
    - [ ]  암호화를 위한 `PasswordEncoder`를 직접 만들어 사용합니다.
    - PasswordEncoder 참고 코드
  <br/>    
  ### Lv 7. 댓글 CRUD 

- [ ]  생성한 일정에 댓글을 남길 수 있습니다.
    - [ ]  댓글과 일정은 연관관계를 가집니다. →  `3주차 연관관계 매핑 참고!`
- [ ]  댓글을 저장, 조회, 수정, 삭제할 수 있습니다.
- [ ]  댓글은 아래와 같은 필드를 가집니다.
    - [ ]  `댓글 내용`, `작성일`, `수정일`, `유저 고유 식별자`, `일정 고유 식별자` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용하여 적용합니다.
  <br/>    


### Lv 8. 일정 페이징 조회  `도전`

- 키워드
    
    **데이터베이스**
    
    - offset / limit : SELECT 쿼리에 적용해서 데이터를 제한 범위에 맞게 조회할 수 있습니다.
    
    **페이징**
    
    - Pageable : Spring Data JPA에서 제공되는 페이징 관련 인터페이스 입니다.
    - PageRequest : Spring Data JPA에서 제공되는 페이지 요청 관련 클래스입니다.
- [ ]  일정을 Spring Data JPA의 `Pageable`과 `Page` 인터페이스를 활용하여 페이지네이션을 구현
    - [ ]  `페이지 번호`와 `페이지 크기`를 쿼리 파라미터로 전달하여 요청하는 항목을 나타냅니다.
    - [ ]  `할일 제목`, `할일 내용`, `댓글 개수`, `일정 작성일`, `일정 수정일`, `일정 작성 유저명` 필드를 조회합니다.
    - [ ]  디폴트 `페이지 크기`는 10으로 적용합니다.
- [ ]  일정의 `수정일`을 기준으로 내림차순 정렬합니다.
