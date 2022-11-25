# JAR(Java Archive)

- 자바 플렛폼에 응용 소프트웨어나 라이브러리를 배포하기 위한 소프트웨어 패키지 파일
- 여러 개의 자바 클래스 파일과 클래스 파일들이 이용하는 관련 리소스(텍스트, 그림 등) 및 메타 데이터를 하나의 파일로 모아 놓은 압축 파일
- 원하는 구조로 구성이 가능함

# WAR(Web Application Archive)

- 웹 애플리케이션의 내용을 담아놓은 파일
- servlet/jsp 컨테이너에 배치할 수 있는 웹 애플리케이션 압축 파일
  - JSP, SERVLET, JAR, CLASS, XML, HTML, JAVASCRIPT 등 Servlet Context 관련 파일들로 패키징되어 있다.
- 웹 응용 프로그램을 위한 포맷
  - 웹 관련 자원만 포함하고 있다.
- 원하는 구성을 할 수 있는 JAR 포맷과 달리 WAR는 WEB-INF 및 META-INF 디렉토리로 사전 정의 된 구조를 사용
- WAR 파일을 실행하려면 Tomcat, Weblogic, Webspere 등의 웹 서버(WEB) 또는 웹 컨테이너가 필요하다.

## 구성 파일(웹 리소스)

- 서버측 유틸리티 클래스
- 클라이언트측 클래스
- 정적 웹 콘텐츠(HTML, image 등)

## 디렉토리 구조

- 최상위 디렉토리(애플리케이션의 문서 루트)
  - JSP 페이지
  - 클라이언트측 클래스 및 아카이브
  - 정적 웹 자원
- WEB-INF 문서 루트
  - web.xml
    - 웹 응용 프로그램 배포 설명자
  - classes
    - 서버 측 클래스를 포함하는 디렉토리
  - lib
    - 라이브러리의 JAR 아카이브를 포함하는 디렉토리

# 참고

<https://ifuwanna.tistory.com/224>

<https://web.archive.org/web/20120626020019/http://java.sun.com/j2ee/tutorial/1_3-fcs/doc/WCC3.html>
