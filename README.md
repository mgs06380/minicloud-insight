# 🌥️ MiniCloud Insight

> Spring Boot 기반 통합 모니터링 시스템

실시간 메트릭 수집, 로그 분석, 알림 시스템을 갖춘 운영 환경 수준의 모니터링 솔루션입니다.

[![Version](https://img.shields.io/badge/version-1.2.0-blue.svg)](https://github.com/your-username/insight/releases)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

---

## 📊 프로젝트 개요

MiniCloud Insight는 Spring Boot 애플리케이션의 성능과 로그를 통합 모니터링하는 시스템입니다.

### 핵심 기능

#### 📈 메트릭 모니터링 (Prometheus + Grafana)
- CPU, 메모리, HTTP 요청 등 실시간 메트릭 수집
- 5개 패널로 구성된 인터랙티브 대시보드
- PromQL 기반 커스텀 쿼리

#### 📝 로그 모니터링 (ELK Stack)
- JSON 형식의 구조화된 로그 수집
- Elasticsearch 기반 전문 검색
- Kibana 대시보드로 로그 시각화 및 분석

#### 🔔 알림 시스템 (Grafana Alerting) *[Week 4 진행 중]*
- 메트릭 임계값 기반 실시간 알림
- Email/Slack 멀티 채널 지원

#### 🚀 CI/CD 파이프라인 (GitHub Actions) *[Week 4 진행 중]*
- 자동 빌드 및 테스트
- Docker 이미지 자동 생성

---

## 🏗️ 시스템 아키텍처
```
┌─────────────────────────────────────────────────────┐
│                 Docker Compose                      │
│                                                     │
│  ┌──────────────┐      ┌──────────────┐            │
│  │  Spring Boot │─────▶│  Prometheus  │            │
│  │    :8080     │      │    :9090     │            │
│  └──────┬───────┘      └──────┬───────┘            │
│         │                     │                    │
│         │                     ▼                    │
│         │              ┌──────────────┐            │
│         │              │   Grafana    │            │
│         │              │    :3000     │            │
│         │              └──────────────┘            │
│         │                                          │
│         │ (로그 파일)                               │
│         ▼                                          │
│  ┌──────────────┐      ┌──────────────┐            │
│  │   Filebeat   │─────▶│Elasticsearch │            │
│  │              │      │    :9200     │            │
│  └──────────────┘      └──────┬───────┘            │
│                                │                   │
│                                ▼                   │
│                        ┌──────────────┐            │
│                        │   Kibana     │            │
│                        │    :5601     │            │
│                        └──────────────┘            │
└─────────────────────────────────────────────────────┘
```

---

## 🛠️ 기술 스택

### Backend
- **Spring Boot 3.x** - 애플리케이션 프레임워크
- **Java 17** - 프로그래밍 언어
- **Gradle** - 빌드 도구

### Monitoring
- **Prometheus** - 메트릭 수집 및 저장
- **Grafana** - 메트릭 시각화
- **Spring Boot Actuator** - 메트릭 노출

### Logging
- **Elasticsearch 8.11.0** - 로그 저장 및 검색
- **Filebeat 8.11.0** - 로그 수집
- **Kibana 8.11.0** - 로그 시각화
- **Logback** - 로그 프레임워크

### Infrastructure
- **Docker** - 컨테이너화
- **Docker Compose** - 멀티 컨테이너 오케스트레이션

---

## 🚀 Quick Start

### 사전 요구사항
- Docker Desktop 설치
- Java 17 이상
- 8080, 3000, 5601, 9090, 9200 포트 사용 가능

### 실행 방법
```bash
# 1. 저장소 클론
git clone https://github.com/your-username/insight.git
cd insight

# 2. 애플리케이션 빌드
./gradlew clean build

# 3. 전체 스택 실행
docker compose up -d

# 4. 로그 확인
docker compose logs -f
```

### 접속 URL

| 서비스 | URL | 설명 |
|--------|-----|------|
| Spring Boot | http://localhost:8080 | 메인 애플리케이션 |
| Swagger UI | http://localhost:8080/swagger-ui.html | API 문서 |
| Actuator | http://localhost:8080/actuator | 헬스체크 & 메트릭 |
| Prometheus | http://localhost:9090 | 메트릭 수집 엔진 |
| Grafana | http://localhost:3000 | 메트릭 대시보드 (admin/admin) |
| Elasticsearch | http://localhost:9200 | 로그 검색 엔진 |
| Kibana | http://localhost:5601 | 로그 대시보드 |

---

## 📊 대시보드

### Grafana 메트릭 대시보드
- **CPU 사용률** - 시스템 CPU 모니터링
- **JVM Heap 메모리** - 힙 메모리 사용량
- **JVM 전체 메모리** - 전체 메모리 추이
- **HTTP 요청 수** - 초당 요청 수 (req/sec)
- **평균 응답 시간** - API 응답 시간

### Kibana 로그 대시보드
- **로그 레벨 분포** - INFO, WARN, ERROR 비율
- **시간별 로그 추이** - 시간대별 로그 발생 패턴
- **Top 에러 메시지** - 가장 많이 발생한 에러
- **Logger별 통계** - 클래스별 로그 발생 현황
- **최근 에러 로그** - 실시간 에러 모니터링

---

## 📝 API 엔드포인트

### 모니터링 API
```bash
# 상태 확인
GET /api/status

# 로그 테스트
GET /api/log-test

# 에러 발생 테스트
GET /api/error?type=runtime
GET /api/error?type=arithmetic
GET /api/error?type=custom
```

### Actuator 엔드포인트
```bash
# 헬스체크
GET /actuator/health

# Prometheus 메트릭
GET /actuator/prometheus

# 메트릭 목록
GET /actuator/metrics
```

---

## 🧪 테스트

### 로그 테스트
```bash
# 여러 로그 레벨 생성
curl http://localhost:8080/api/log-test

# Kibana에서 확인
http://localhost:5601/app/discover
```

### 에러 테스트
```bash
# RuntimeException 발생
curl "http://localhost:8080/api/error?type=runtime"

# Kibana에서 Stack Trace 확인
```

### 부하 테스트
```bash
# 100회 요청
for i in {1..100}; do 
  curl http://localhost:8080/api/status
done

# Grafana에서 메트릭 증가 확인
```

---

## 📦 프로젝트 구조
```
insight/
├── .github/
│   └── workflows/          # GitHub Actions (Week 4)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/minicloud/insight/
│       │       ├── controller/
│       │       ├── service/
│       │       └── MiniCloudInsightApplication.java
│       └── resources/
│           ├── application.yml
│           └── logback-spring.xml
├── prometheus/
│   └── prometheus.yml      # Prometheus 설정
├── filebeat/
│   └── filebeat.yml        # Filebeat 설정
├── docker-compose.yml      # 전체 스택 설정
├── Dockerfile
├── build.gradle
└── README.md
```

---

## 🎯 프로젝트 진행 상황

### ✅ Week 1: Docker 환경 구축
- Docker Compose 멀티 컨테이너 환경
- Spring Boot, Prometheus, Grafana 연동

### ✅ Week 2: Prometheus + Grafana
- 실시간 메트릭 수집 (5초 간격)
- 5개 패널 대시보드 구성
- CPU, 메모리, HTTP 요청 모니터링

### ✅ Week 3: ELK Stack
- Elasticsearch, Filebeat, Kibana 설치
- JSON 로그 수집 및 인덱싱
- 5개 패널 로그 대시보드 구성

### 🚧 Week 4: Alerting & CI/CD (진행 중)
- Grafana Alerting 설정
- GitHub Actions CI/CD 파이프라인

---

## 📚 문서

- [Week 1 개발 일지](docs/week1.md)
- [Week 2 개발 일지](docs/week2.md)
- [Week 3 개발 일지](docs/week3.md)
- [Grafana 대시보드 가이드](docs/grafana-guide.md)
- [Kibana 대시보드 가이드](docs/kibana-guide.md)

---

## 🔧 트러블슈팅

### Elasticsearch 시작 안 됨
```bash
# 메모리 부족 시
docker compose restart elasticsearch
```

### Kibana 연결 안 됨
```bash
# Elasticsearch 준비 대기 (1-2분)
docker compose logs -f kibana
```

### 로그가 Kibana에 안 보임
```bash
# 시간 범위를 "Last 24 hours"로 변경
# Filebeat 재시작
docker compose restart filebeat
```

---

## 📈 버전 히스토리

### [v1.2.0] - 2025-11-03
- ELK Stack 로그 모니터링 추가
- Kibana 대시보드 5개 패널 구성
- JSON 구조화 로그 시스템

### [v1.1.0] - 2025-10-XX
- Grafana 대시보드 추가
- Prometheus 메트릭 수집

### [v1.0.0] - 2025-10-XX
- 초기 Spring Boot 애플리케이션
- Docker 환경 구축

---

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

---

## 👤 Author

**조석훈**

- GitHub: [@your-username](https://github.com/mgs06380)
- Email: your-email@example.com

---

## 🙏 Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Prometheus](https://prometheus.io/)
- [Grafana](https://grafana.com/)
- [Elastic Stack](https://www.elastic.co/)

---

## 📞 Contact

프로젝트에 대한 질문이나 제안사항이 있으시면 이슈를 등록해주세요!

---

**⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!**
