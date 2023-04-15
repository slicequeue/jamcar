# Jamcar Rest API Server
자차로 출퇴근 길 교통상황에 대해서 파악할 수 있는 구간 교통 수집 프로그램 Rest API Server
- 프로젝트 페이지: https://www.notion.so/85bbe11727904d918fd9bdf469e83b11?v=e04bd22ca84a4b389e2a0370a4dbf764&pvs=4


## 구성
Spring Boot RESTful API 

### 폴더 구조
- TBU

### 사용 라이브러리
build.gradle 구성 내용 설명
* JAVA 17
#### plugin
* 'org.springframework.boot' version 2.7.5
#### dependencies
* spring-boot-starter 관련 - plugin-version 2.7.5
  * spring-boot-starter-web
  * spring-boot-starter-test - junit jupiter
  * spring-boot-starter-actuator
* micrometer & prometheus
  * io.micrometer:micrometer-registry-prometheus:1.8.4
* logback & log4j 취약점 대응
  * ch.qos.logback:logback-core:1.2.10
  * ch.qos.logback:logback-classic:1.2.10
  * org.slf4j:slf4j-api:1.7.32
  * org.slf4j:jul-to-slf4j:1.7.32
  * org.apache.logging.log4j:log4j-to-slf4j:2.17.1
  * org.apache.logging.log4j:log4j-api:2.17.1

## 초기 세팅
프로젝트 초기 세팅 관련 설정법 기술

### 액티브프로파일 설정
* jvm active profile 값 설정, IntelliJ 실행 설정으로 처리
  * 기본 설정값 관련해서는 예시용으로 작성한 logback-local.xml `local` 로 설정해야 작동
