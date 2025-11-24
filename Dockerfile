# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Gradle 파일 복사
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# 소스 코드 복사
COPY src ./src

# 애플리케이션 빌드
RUN gradle clean build -x test

# 2단계: 실행 스테이지
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# curl 설치 (헬스체크용)
RUN apk add --no-cache curl

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 헬스체크 추가 (CI에서 사용)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]