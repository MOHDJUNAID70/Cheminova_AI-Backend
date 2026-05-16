<div align="center">

# 🚀 ChemiNova — AI-Powered Career Guidance Platform

### *Analyze your skills. Find the gaps. Build your future.*

[![Java](https://img.shields.io/badge/Java_17-Spring_Boot_3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/)
[![React](https://img.shields.io/badge/React.js-Frontend-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://reactjs.org/)
[![Python](https://img.shields.io/badge/Python-AI_Service-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://python.org/)
[![Redis](https://img.shields.io/badge/Redis-Token_Store-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docker.com/)
[![Railway](https://img.shields.io/badge/Railway-Deployed-0B0D0E?style=for-the-badge&logo=railway&logoColor=white)](https://railway.app/)

<br/>

> A full-stack, production-deployed platform where users input their current skills and career goal — and receive an AI-generated, personalized learning roadmap with course recommendations, skill gap analysis, and timeline estimation.

<br/>

[🌐 Live Demo](https://cheminova-class.vercel.app) &nbsp;·&nbsp; [📖 API Docs](https://cheminova-ai-backend.onrender.com/swagger-ui/index.html) &nbsp;·&nbsp;

</div>

---

## 📌 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [API Reference](#-api-reference)
- [Security Implementation](#-security-implementation)
- [Database Design](#-database-design)
- [Project Structure](#-project-structure)
- [Running Locally](#-running-locally)
- [Team](#-team)

---

## 🎯 Overview

CheminNova is a **collaborative, microservice-inspired full-stack application** built by a 3-person team, each owning a separate service — React.js frontend, Java Spring Boot backend, and a Python AI service — all integrated and containerized using Docker.

The platform solves a real problem: **students and professionals don't know what to learn next.** ChemiNova analyzes their current skills, compares them against industry requirements for their target role, identifies the gaps, and generates a step-by-step learning path — powered by an LLM-based AI engine.

---

## ✨ Key Features

**AI & Career Intelligence**
- 🤖 Personalized learning path generation via LLM-based Python AI service
- 📊 Skill gap analysis with match percentage scoring
- 🗺️ Phased learning roadmap (Foundation → Core Skills → Projects)
- ⏱️ Estimated completion timeline based on daily study hours
- 💬 AI-powered career guidance chatbot
- 🔁 Duplicate request prevention — same input returns cached DB result, saving AI compute costs

**Authentication & Security**
- 🔐 JWT-based stateless authentication
- 📧 Email OTP verification on registration (via SMTP)
- 🚫 Redis-based token blacklisting on logout and account deletion
- 🛡️ CORS policy restricted to authorized frontend domains only
- 🔒 Spring Security filter chain with route protection

**Data & User Management**
- 💾 Persistent learning path storage linked to user accounts
- 🗑️ Cascade delete — all user data auto-removed on account deletion
- ⭐ Dynamic course rating system using weighted average formula
- 👥 Real-time platform stats (total users, total paths generated)

---

## 🏗️ System Architecture

```
                   ┌──────────────────────────────┐
                   │      React.js Frontend        │
                   │    (Vercel — Production)       │
                   └─────────────┬────────────────┘
                                 │ HTTPS + JWT
                                 ▼
                   ┌──────────────────────────────┐
                   │  Java Spring Boot Backend     │◄──── Redis
                   │  (Railway — Production)       │   (Token Blacklist)
                   │                              │
                   │  • JWT Auth & Verification   │
                   │  • CORS Policy Enforcement   │
                   │  • Request Validation         │
                   │  • DB Persistence            │
                   │  • Duplicate Detection        │
                   └─────────────┬────────────────┘
                                 │ HTTP (Internal)
                                 ▼
                   ┌──────────────────────────────┐
                   │      Python AI Service        │
                   │    (Railway — Production)     │
                   │                              │
                   │  • LLM-based Path Gen        │
                   │  • Skill Gap Analysis         │
                   │  • Career Matching            │
                   │  • AI Chatbot                │
                   └──────────────────────────────┘
```

The Spring Boot backend acts as a **secure middleware layer** — it authenticates every request, enforces business rules, handles persistence, and proxies validated requests to the AI service.

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| **Frontend** | React.js, Axios | User interface, deployed on Vercel |
| **Backend** | Java 17, Spring Boot 3+ | REST API, auth, business logic |
| **AI Service** | Python, LLM, NLP | Career analysis, chatbot |
| **Auth** | Spring Security, JWT | Stateless authentication |
| **Token Store** | Redis | JWT blacklisting |
| **Database** | PostgreSQL | Persistent data storage |
| **ORM** | JPA / Hibernate | Database abstraction |
| **Email** | JavaMailSender, SMTP | OTP verification emails |
| **Containerization** | Docker, Docker Compose | Multi-service orchestration |
| **Deployment** | render (Backend), Vercel (Frontend) | Production hosting |
| **API Docs** | Swagger / OpenAPI 3 | Interactive API documentation |

---

## 📡 API Reference

### Auth Endpoints
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/auth/register` | Register + send OTP email | Public |
| POST | `/auth/verify-otp` | Verify email OTP | Public |
| POST | `/auth/resend-otp` | Resend OTP | Public |
| POST | `/auth/login` | Login → returns JWT | Public |
| POST | `/auth/logout` | Logout + blacklist token | JWT |
| DELETE | `/auth/delete` | Delete account + cascade | JWT |
| GET | `/auth/stats` | Total users, course & paths | Public |

### Career & AI Endpoints
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| POST | `/user/generate-path` | Generate AI learning path | JWT |
| POST | `/api/chat` | AI chatbot message | JWT |

### Course Endpoints
| Method | Endpoint | Description | Auth |
|---|---|---|---|
| GET | `/course/all-courses` | Get all courses with pagination and Filtering | Public |
| POST | `/course/add-course` | Add new course | Admin |
| PUT | `/course/update/{id}` | Update course info | Admin |
| DELETE | `/course/delete/{id}` | Delete course | Admin |
| PATCH | `/course/{id}/rate` | Submit course rating | JWT |
| PATCH | `/course/{id}/enroll` | Enroll in course | JWT |

### Sample Request / Response

```json
POST /api/career-suggest
Authorization: Bearer <token>

{
  "skills": { "Python": 8, "Java": 7, "DSA": 5 },
  "goal": "Software Development Engineer",
  "daily_study_hours": 5
}
```

```json
{
  "suggested_goal": "Certified Software Engineer",
  "confidence_score": 0.39,
  "skill_match_percentage": 60,
  "missing_skills": ["System Design", "Algorithms"],
  "learning_path": [
    { "phase": 1, "title": "Foundation", "topics": ["Data Structures", "Algorithms"] },
    { "phase": 2, "title": "Core Skills", "topics": ["System Design"] },
    { "phase": 3, "title": "Projects & Portfolio", "topics": ["Build 2-3 real-world projects"] }
  ],
  "recommended_courses": [
    { "title": "Algorithms Mastery", "platform": "Coursera", "skill": "Algorithms" }
  ],
  "timeline_estimation": {
    "estimated_total_hours": 160,
    "estimated_duration_weeks": 4.6
  }
}
```

---

## 🔐 Security Implementation

### JWT Authentication Flow
```
Login → JWT Generated → Stored on client
Every request → Authorization: Bearer <token>
JWT Filter → Validates token → Checks Redis blacklist
Valid + Not Blacklisted → Request proceeds ✅
Invalid or Blacklisted  → 401 Unauthorized  ❌
```

### Token Blacklisting with Redis
When a user logs out or deletes their account, the token is stored in Redis with a TTL equal to the token's remaining expiry time. Redis auto-expires the entry — no manual cleanup needed.

```
Logout / Account Delete
        ↓
Token stored → "blacklist:<token>" with TTL = remaining expiry
        ↓
Same token used again → Redis key exists → 401 Blocked ❌
        ↓
Token naturally expires → Redis key auto-deleted ✅
```

### Email OTP Verification
New users cannot login until their email is verified. OTPs are 6-digit, expire in 10 minutes, and are cleared from the database after successful verification.

### CORS Policy
API access is restricted to explicitly whitelisted frontend origins only. Any request from an unlisted domain is rejected before reaching the application layer.

---

## 🗄️ Database Design

```
┌──────────────┐          ┌───────────────────────┐
│    users     │          │    learning_paths      │
│──────────────│          │───────────────────────│
│ id (PK)      │ 1──────► │ id (PK)               │
│ name         │   Many   │ user_id (FK)           │
│ email        │          │ input_skills (JSON)    │
│ password     │          │ input_goal             │
│ verified     │          │ input_daily_hours      │
│ otp          │          │ suggested_goal         │
│ otp_expiry   │          │ confidence_score       │
│ created_at   │          │ missing_skills (JSON)  │
└──────────────┘          │ learning_path (JSON)   │
                          │ recommended_courses    │
                          │ timeline_hours         │
                          │ timeline_weeks         │
                          │ created_at             │
                          └───────────────────────┘

┌───────────────────────┐
│        courses        │
│───────────────────────│
│ id (PK)               │
│ title                 │
│ description           │
│ category              │
│ duration              │
│ lecture               │
│ rating  (dynamic avg) │
│ students (dynamic)    │
│ price                 │
│ image                 │
│ skills (JSON)         │
│ created_at            │
│ updated_at            │
└───────────────────────┘
```

**Cascade Delete** — `@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)` ensures all learning paths are automatically deleted when a user account is removed, maintaining data integrity without manual queries.

**Duplicate Prevention** — Before calling the AI service, the backend queries the DB for an existing path with the same `user + goal + daily_hours + skills (sorted)`. If found, the stored result is returned directly — saving AI compute and reducing response time.

**Dynamic Course Rating** — Uses weighted average formula:
```
New Average = (currentRating × students + newRating) / (students + 1)
```
Ensures an accurate rolling average across all user submissions.

---

## 📂 Project Structure

```
cheminova/
│
├── backend/                           # Spring Boot (Java 17+)
│   ├── src/main/java/
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── AIController.java
│   │   │   ├── ChatController.java
│   │   │   └── CourseController.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   ├── AIService.java
│   │   │   ├── ChatService.java
│   │   │   ├── CourseService.java
│   │   │   ├── EmailService.java
│   │   │   ├── JwtService.java
│   │   │   └── TokenBlacklistService.java
│   │   ├── model/
│   │   │   ├── entity/
│   │   │   │   ├── Users.java
│   │   │   │   ├── LearningPath.java
│   │   │   │   └── Courses.java
│   │   │── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── CorsConfig.java
│   │   │   ├── SwaggerConfig.java
│   │   │   └── RedisConfig.java
│   │   ├── jwt/
│   │   │   └── JwtFilter.java
│   │   ├── mapper/
│   │   │   └── LearningPathMapper.java
│   │   └── otp/
│   │       └── OtpUtil.java
│   └── Dockerfile
```

---

## 🚀 Running Locally

### Prerequisites
- Java 17+, redis
- Docker & Docker Compose

### One Command Setup

```bash
git clone https://github.com/MOHDJUNAID70/Cheminova_AI-Backend
cd Cheminova_AI-Backend
docker-compose up --build
```

| Service | Local URL |
|---|---|
| Backend API | http://localhost:9215 |
| Swagger Docs | http://localhost:9215/swagger-ui/index.html |

### Backend Environment Variables

```properties
AI_SERVICE_URL=http://localhost:5000
GMAIL_USERNAME=your-email@gmail.com
GMAIL_PASSWORD=your-app-password
SPRING_DATA_REDIS_URL=redis://localhost:6379
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/cheminova
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your-db-password
```

---

## 👥 Team

| Role | Responsibility |
|---|---|
| **Backend Developer** | Spring Boot REST API, JWT Auth, Redis, DB Design, Docker |
| **Frontend Developer** | React.js UI, API Integration, Vercel Deployment |
| **AI Engineer** | Python AI Service, LLM Integration, Career Analysis, Chatbot |

---

## 📄 License

This project is licensed under the MIT License.

---

<div align="center">

**⭐ If you found this project impressive, please give it a star!**

<br/>

Made with ❤️ by the ChemiNova Team

</div>
