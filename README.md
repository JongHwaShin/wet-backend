# WET Backend (What to Eat Today)

> 🍽️ 카카오 지도 API 기반 맛집 검색 백엔드 서비스

Spring Boot로 개발된 RESTful API 서버로, 카카오 로컬 API를 활용하여 주소 기반 맛집 검색 기능을 제공합니다.

## 🚀 주요 기능

- **카카오 지도 API 연동**: 키워드 기반 장소 검색
- **맛집 검색 API**: 주소를 입력받아 주변 식당 정보 반환
- **로깅 시스템**: 날짜별 로그 파일 자동 생성 및 검색 기록 저장
- **CORS 설정**: 프론트엔드 연동을 위한 교차 출처 리소스 공유 지원

## 🛠️ 기술 스택

- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **Database**: MariaDB
- **ORM**: JPA/Hibernate
- **Logging**: Logback
- **Build Tool**: Gradle

## 📦 설치 및 실행

### 사전 요구사항
- JDK 17 이상
- MariaDB 10.x 이상
- Gradle 8.x

### 환경 설정

`src/main/resources/application.properties` 파일을 수정하세요:

```properties
# MariaDB 설정
spring.datasource.url=jdbc:mariadb://localhost:3306/wetdb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

# 카카오 API 키 (필수!)
kakao.api.key=YOUR_KAKAO_REST_API_KEY
```

### 실행

```bash
# Gradle로 실행
./gradlew bootRun

# 또는 JAR 빌드 후 실행
./gradlew build
java -jar build/libs/wet-backend-0.0.1-SNAPSHOT.jar
```

서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.

## 📡 API 엔드포인트

### 맛집 검색
```http
GET /api/restaurants/search?address={주소}
```

**Parameters:**
- `address` (required): 검색할 주소 (예: "서울시 강남구 역삼동")

**Response:**
```json
{
  "documents": [
    {
      "place_name": "맛집 이름",
      "road_address_name": "도로명 주소",
      "category_name": "음식점 > 한식 > 고기요리",
      "phone": "전화번호",
      "x": "경도",
      "y": "위도"
    }
  ],
  "meta": {
    "total_count": 45,
    "pageable_count": 45
  }
}
```

## 📂 프로젝트 구조

```
src/main/java/com/example/wetbackend/
├── controller/
│   └── RestaurantController.java    # 맛집 검색 API 컨트롤러
├── service/
│   └── KakaoMapService.java         # 카카오 API 호출 서비스
├── config/
│   └── WebConfig.java               # CORS 설정
└── WetBackendApplication.java       # 메인 애플리케이션

src/main/resources/
├── application.properties            # 애플리케이션 설정
└── logback-spring.xml               # 로깅 설정
```

## 📝 로그 파일

로그는 `logs/` 디렉토리에 날짜별로 자동 생성됩니다:
- 파일명 형식: `wet-backend-yyyy-MM-dd.log`
- 보관 기간: 30일
- 기록 내용: 검색 요청 주소, 검색 결과 데이터

## 🔑 카카오 API 키 발급

1. [Kakao Developers](https://developers.kakao.com/) 접속
2. 애플리케이션 생성
3. **REST API 키** 복사
4. `application.properties`에 설정

## 🔗 관련 프로젝트

- [WET Frontend](https://github.com/JongHwaShin/wet) - Flutter 기반 모바일 앱

## 📝 라이선스

This project is licensed under the MIT License.

## 👨‍💻 개발자

JongHwa Shin
