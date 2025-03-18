# AMUZ 개발자 채용 과제
괜련 자세한 사항은 amuz-front의 README에 통합하여 작성해 두었습니다.

# 사전 준비

## 백엔드 실행
### 설치
```bash
git clone https://github.com/workmakerdaily/amuz-back.git
cd amuz-back
```

### 실행
```bash
# 혹은 우측 상단 실행 버튼 클릭
./gradlew bootRun
```

### application.properties
application.properties는는 메일로 파일 첨부해 두었습니다.
해당 파일 사용하시면서 아래 내용 참고하여 수정하시면 됩니다.
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://{호스트명}:{포트번호}/{데이터베이스명}?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username={사용자명}
spring.datasource.password={비밀번호}
```