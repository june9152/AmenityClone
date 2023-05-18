# 여기 어떠니



>개발 기간: 2023.05.11 ~ 2023.05.18</p>🎥 시연영상 

### 여기어떠니
  - [여기어때] 크롤링 프로젝트 입니다
  - 호텔, 펜션 정보를 조회, 예약(실제 결제는 안됨)을 할 수 있도록 구현하는것을 목표로 했습니다.
  - 다중 이미지 조회, 업로드 및 QueryDsl 이용한 동적 쿼리 등 기본기를 다지는 것을 중점으로 하였습니다.

## 📃 S.A
(https://www.notion.so/S-A-afa6ceba07db4022bebde44ccb35807a)

## 📜와이어 프레임
<!-- 
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/d5c2eaec-d3ae-4482-8fb1-308dfcd61749).png" width="200" height="400"/>
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/f84a03b9-8f04-4bbf-bb12-71cb1098e61b).png" width="200" height="400"/>
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/f7ac4316-166c-4ce8-9851-6a5d347daa82).png" width="200" height="400"/>
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/e94cc01c-8e08-4073-aa88-852bfa32a27f).png" width="200" height="400"/> -->

![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/98ed39c4-c173-4938-9a99-319bd35718fb)
![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/7fb5f467-79cd-4a3c-ac30-61bfe173bea1)
![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/9d02227a-e18b-4b7d-9fe6-7501fcf03c4d)



## 📰 ERD
![erd](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/291756b4-a0a8-420f-91a7-dfa8a029c504)

 
## 📖 API 명세서
<details>
  <summary> 펼쳐보기 </summary>
<img width="964" alt="hh66 API 명세서" src="">
</details>

## 👨‍👩‍👧팀원
<!-- |이름|역할|
|------|---|
|박영준(BE팀장)</br>[@june9152](https://github.com/june9152)|- 댓글 API</br>- 게시글, 댓글 좋아요 API</br>- 서버 배포</br>- 검색 기능</br>- DB 연동|
|김은양</br>[@silversheep26](https://github.com/silversheep26)|- RefreshToken, AccessToken</br>- 유저 API </br>- entity 연관관계|
|고예진</br>[@YEJINGO](https://github.com/YEJINGO)|-게시글 API</br>-  검색 기능|
|정성윤</br>[@kanteluv](https://github.com/kanteluv)|- 댓글 API </br>- 게시글, 댓글 좋아요 API </br>-  검색 기능| -->

|이름|깃허브|
|------|---|
|박영준(BE팀장)|[@june9152](https://github.com/june9152)|
|김은양|[@silversheep26](https://github.com/silversheep26)|
|고예진|[@YEJINGO](https://github.com/YEJINGO)|
|정성윤|[@kanteluv](https://github.com/kanteluv)|

FE git hub : https://github.com/AmenityCloneCoding/AmenityClone

## ⚙️ Tech Stack
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <br>
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> <br>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

## 1️⃣ 회원 관련 기능
  1. 회원 가입 API <br>
    - userEmail : 이메일 형식/ **중복 불가** <br>
    - password : 크기 8 이상, 15 이하 / 대소문자, 숫자, 특수문자 가능, 공백 금지 <br>
    - userNickNm  <br>

    
  2. 로그인 / 로그아웃 API<br>
    - 로그인 성공 시, Access Token과 Refresh Token을 발급하고 헤더에 토큰을 추가<br>
  
## 2️⃣ 숙박 업소 조회 관련 기능
  1. 호텔/펜션 전체 목록 조회 API <br>
    - 메인 페이지에서 호텔 또는 펜션을 누른 후 목록 조회 <br>
  
  2. 호텔/펜션 선택 목록 조회(필터) API <br>
    - 호텔/펜션 전체 목록 조회 후 상세 조건(필터) 선택 후 목록 조회  <br>
    
  3. 숙박 업소 상세 조회 API <br>
    - 하나의 숙박 업소 선택 시 객실정보, 리뷰 조회 <br>

  4. 검색으로 숙박 업소 조회 API <br>
    - 내용 또는 이름에 키워드가 들어 있는 숙박 업소 조회 <br>


## 3️⃣ 예약 관련 기능
  1. 예약 등록 API <br>
    - 상세 조회에서 예약하기 누른 경우  <br>
    
  2. 예약 조회 API <br>
    - 자신이 예약한 내용 조회 <br>
    
  3. 예약 날짜 조회 API <br>
    - 예약 등록 시 체크인/아웃 날짜를 이용하여 숙박 일 확인 <br>

  4. 예약 삭제 조회 API <br>
    - 예약 등록한 것 삭제 <br>
  
## 4️⃣ 리뷰 기능

  1. 로그인 없이 리뷰 조회 API <br>
    - 로그인 없이 리뷰만 조회 <br>
  
  2. 로그인 후 리뷰 조회 API <br>
    - 리뷰 조회 및 리뷰 작성 <br>

## 5️⃣ 이미지 업로드 기능

  1. 이미지 업로드 API <br>
    - 이미지 업로드만을 위한 기능 <br>


## 🚩 기능 구현을 위해 고민한 것

클론코딩을 준비하면서 테이블과 연관관계를 어떻게 구성할지 명세를 팀원과 많이 고민하여 구성하였다. <br>
특히 숙박업소 - 객실 - 리뷰 그리고 각 이미지 url 테이블까지 총 6개 테이블의 데이터를 한번의 요청으로 내려주는 로직 구성이 힘들었다. <br>
rds, s3, ec2 경험해본사람이 없어 연습해볼 수 있는 좋은 기회가 되었고, 단일, 다중 이미지 업로드 구현까지 해 보았다. <br>
연관관계가 구성되어있어 전부 dto를 이용하여 감싸 처리하였는데, 상세조건별 조회를 반영하기 위해 Querydsl 과 연산로직으로 데이터를 뽑아내어 속도가 느려져 아쉬웠다.<br>
