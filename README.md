# 여기 어떠니



>개발 기간: 2023.05.11 ~ 2023.05.18</p>🎥 시연영상 

### 여기어떠니
  - [여기어때] 크롤링 프로젝트 입니다
  - 호텔, 펜션 정보를 조회, 예약(실제 결제는 안됨)을 할 수 있도록 구현하는것을 목표로 했습니다.
  - 다중 이미지 조회, 업로드 및 QueryDsl 이용한 동적 쿼리 등 기본기를 다지는 것을 중점으로 하였습니다.

## 📃 S.A
(https://www.notion.so/S-A-afa6ceba07db4022bebde44ccb35807a)

## 📜와이어 프레임

<!-- <img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/d5c2eaec-d3ae-4482-8fb1-308dfcd61749).png" width="200" height="400"/>
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/f84a03b9-8f04-4bbf-bb12-71cb1098e61b).png" width="200" height="400"/>
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/f7ac4316-166c-4ce8-9851-6a5d347daa82).png" width="200" height="400"/>
<img src="![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/e94cc01c-8e08-4073-aa88-852bfa32a27f).png" width="200" height="400"/> -->

![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/98ed39c4-c173-4938-9a99-319bd35718fb)
![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/7fb5f467-79cd-4a3c-ac30-61bfe173bea1)
![image](https://github.com/AmenityCloneCoding/AmenityClone/assets/122955367/9d02227a-e18b-4b7d-9fe6-7501fcf03c4d)



## 📰 ERD

<!-- <img src="이미지주소.png" width="200" height="400"/>
 -->
 
## 📖 API 명세서
<details>
  <summary> 펼쳐보기 </summary>
<img width="964" alt="hh66 API 명세서" src="https://github.com/seunghee58/miniproject_hanhae66/assets/129656095/719cd245-0070-4e8d-9870-8fb55d2f5265">
</details>

## 👨‍👩‍👧팀원
|이름|역할|
|------|---|
|박영준(BE팀장)</br>[@june9152](https://github.com/june9152)|- 댓글 API</br>- 게시글, 댓글 좋아요 API</br>- 서버 배포</br>- 검색 기능</br>- DB 연동|
|김은양</br>[@silversheep26](https://github.com/silversheep26)|- RefreshToken, AccessToken</br>- 유저 API </br>- entity 연관관계|
|고예진</br>[@YEJINGO](https://github.com/YEJINGO)|-게시글 API</br>-  검색 기능|
|정성윤</br>[@kanteluv](https://github.com/kanteluv)|- 댓글 API </br>- 게시글, 댓글 좋아요 API </br>-  검색 기능|

FE git hub : https://github.com/HaeJinS2/MiniProject_Hanghae66_FE

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
    - 로그인 없이 리뷰만 조회 가능 <br>
  
  2. 로그인 후 리뷰 조회 API <br>
    - 리뷰 조회 및 리뷰 작성 가능 <br>

## 5️⃣ 이미지 업로드 기능

  1. 이미지 업로드 API <br>
    - 키워드를 입력하여 **제목+내용, 제목, 내용**으로 게시글 조회 <br>


## 🚩 기능 구현을 위해 고민한 것

그간 항해를 진행하면서 익혀왔던 테이블간의 연관관계를 구성하고 데이터를 가져오는 방법을 개선하고 싶었는데, <br>
서로 바라보고 있는 양방향관계에서는 순환참조가 일어날 수 밖에 없다고 생각 <br>

추천하는 방법이 @JsonIgnore, @JsonManagedReference, @JsonBackReference 과 같은 어노테이션을 사용하는 것과 <br>
DTO를 이용한 방법이 있었고 <br>
어노테이션을 이용한 방법은 정확히 어떻게 순환참조가 되지 않는지 이해가 잘 안되서 DTO를 이용하는 방법으로 CRUD 를 구현 <br>

DTO를 각 엔티티의 순환참조가 생길만한 연관관계를 지정하는 멤버를 제외하고 본따서 만듦 <br>
게시글과 댓글이 서로 양방향 관계라고 했을 때, 게시글은 댓글 멤버를, 댓글은 게시글 멤버를 뺀 DTO를 구성하고, 각각의  DTO를 더해서 응답 DTO를 만들어내는 로직을 구성 <br>

이렇게 되면 순환참조나 N+1 같은 문제를 피할 수 있게 되지만 구현하다 보니 원하는 출력값이 달라질 때마다 응답 DTO가 계속 추가됨 <br>

프로젝트를 끝내면서 생각해보니 안전하지만 코드가 길어지고 클래스가 늘어나는 DTO를 이용하는 방법도 최선이 아니겠다 생각을 함 <br>
