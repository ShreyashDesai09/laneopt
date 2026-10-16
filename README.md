# 🚚 LaneOpt — Freight Transportation Cost Optimization Engine

<p align="center">
  <strong>A Java Spring Boot backend for intelligent transporter assignment and transportation cost optimization.</strong>
</p>

<p align="center">
  <a href="https://laneopt.onrender.com">
    <img src="https://img.shields.io/badge/Live%20Application-Visit%20LaneOpt-16a34a?style=for-the-badge" alt="Live Application">
  </a>
  <a href="https://laneopt.onrender.com/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/API%20Documentation-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger API Documentation">
  </a>
  <a href="https://www.loom.com/share/8cf8804e58794ed18dd84d9d203e81f0">
    <img src="https://img.shields.io/badge/Project%20Demo-Watch%20Video-625DF5?style=for-the-badge&logo=loom&logoColor=white" alt="Project Demonstration">
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-Backend-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=flat-square&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Docker-Containerization-2496ED?style=flat-square&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Deployment-Render-46E3B7?style=flat-square&logo=render&logoColor=black" alt="Render">
  <img src="https://img.shields.io/badge/API%20Docs-OpenAPI-85EA2D?style=flat-square&logo=swagger&logoColor=black" alt="OpenAPI">
</p>

---

## 📌 Project Overview

LaneOpt is a backend application developed as part of the **FreightFox Backend Assignment — Transporter Assignment on Lanes**.

The application optimizes transporter assignments across logistics lanes by considering transportation costs, transporter utilization, and complete lane coverage.

It accepts lane information, transporter details, and their corresponding transportation quotes. Based on these inputs and a user-defined transporter limit, the application generates assignments according to the optimization priorities implemented in the service layer.

The application uses PostgreSQL for persistent data storage and exposes REST APIs through Spring Boot. Swagger UI provides interactive API documentation, while Docker and Render are used for deployment.

### 🎯 Key Objectives

- **Cost Optimization:** Minimize the total transportation cost while satisfying the assignment constraints.
- **Transporter Utilization:** Maximize the number of distinct transporters used within the user-defined limit.
- **Complete Lane Coverage:** Ensure that every lane receives a valid transporter assignment.
- **Persistent Storage:** Store lanes, transporters, and their quotes in PostgreSQL.
- **RESTful API Design:** Provide endpoints for submitting input, calculating assignments, and retrieving stored data.
- **Interactive Documentation:** Enable API exploration and testing through Swagger UI.

---

## 🔗 Project Links

| Resource | Link |
|---|---|
| 🌐 Live Application | [Open LaneOpt](https://laneopt.onrender.com) |
| 📘 Swagger UI | [Explore and Test APIs](https://laneopt.onrender.com/swagger-ui/index.html) |
| 🎥 Project Demonstration | [Watch the Loom Video](https://www.loom.com/share/8cf8804e58794ed18dd84d9d203e81f0) |
| 💻 Source Code | [View GitHub Repository](YOUR_GITHUB_REPOSITORY_URL) |

> **Note:** Replace `YOUR_GITHUB_REPOSITORY_URL` with the actual URL of this repository.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Primary programming language |
| Spring Boot | Backend application framework |
| Spring Web MVC | REST API development |
| Spring Data JPA | Database access and persistence |
| Hibernate | Object-relational mapping |
| PostgreSQL | Relational database |
| Maven | Dependency management and build automation |
| Springdoc OpenAPI | API documentation and Swagger UI |
| Docker | Application containerization |
| Render | Application and database hosting |

---

## 🏗️ Application Architecture

LaneOpt follows a layered architecture to separate API handling, business logic, and database operations.

```text
Client / Swagger UI
        |
        v
   Controllers
        |
        v
     Services
        |
        v
   Repositories
        |
        v
    PostgreSQL
```

### Layer Responsibilities

- **Entity:** Represents the core domain objects, including lanes, transporters, and transporter quotes.
- **Repository:** Provides database operations through Spring Data JPA.
- **DTO:** Defines the request and response data structures used by the APIs.
- **Service:** Implements the business logic for input processing and transporter assignment.
- **Controller:** Exposes REST endpoints and handles incoming HTTP requests.
- **HomeController:** Redirects the application's root URL to Swagger UI, providing direct access to the API documentation.

---

## 🚀 Live API Endpoints

The application exposes the following REST endpoints.

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/transporters/input` | Submit lanes, transporters, and lane quotes |
| `POST` | `/api/v1/transporters/assignment` | Calculate transporter assignments |
| `GET` | `/api/v1/lanes` | Retrieve all lanes |
| `GET` | `/api/v1/transporters` | Retrieve all transporters |
| `GET` | `/api/v1/lanes/{laneId}/quotes` | Retrieve quotes associated with a specific lane |

**Base URL**

```text
https://laneopt.onrender.com
```

**API Documentation**

[Open Swagger UI](https://laneopt.onrender.com/swagger-ui/index.html)

---

## 1️⃣ Submit Transporter Input

Submits lane details, transporter information, and transportation quotes.

### Request Details

**Method:** `POST`

**Endpoint:**

```text
/api/v1/transporters/input
```

**Full URL:**

```text
https://laneopt.onrender.com/api/v1/transporters/input
```

**Headers:**

```http
Content-Type: application/json
```

### Sample Request Body

The following example uses Bengaluru as the central origin and five destinations across major Indian cities.

```json
{
  "lanes": [
    {
      "id": 1,
      "origin": "Bengaluru",
      "destination": "Pune",
      "description": "Bengaluru to Pune transportation lane"
    },
    {
      "id": 2,
      "origin": "Bengaluru",
      "destination": "Mumbai",
      "description": "Bengaluru to Mumbai transportation lane"
    },
    {
      "id": 3,
      "origin": "Bengaluru",
      "destination": "Chennai",
      "description": "Bengaluru to Chennai transportation lane"
    },
    {
      "id": 4,
      "origin": "Bengaluru",
      "destination": "Hyderabad",
      "description": "Bengaluru to Hyderabad transportation lane"
    },
    {
      "id": 5,
      "origin": "Bengaluru",
      "destination": "Noida",
      "description": "Bengaluru to Noida transportation lane"
    }
  ],
  "transporters": [
    {
      "id": 101,
      "name": "Namma Karnataka Logistics",
      "laneQuotes": [
        {
          "laneId": 1,
          "quote": 14500
        },
        {
          "laneId": 2,
          "quote": 16500
        },
        {
          "laneId": 3,
          "quote": 8500
        },
        {
          "laneId": 4,
          "quote": 7800
        },
        {
          "laneId": 5,
          "quote": 26500
        }
      ]
    },
    {
      "id": 102,
      "name": "Deccan Express Transport",
      "laneQuotes": [
        {
          "laneId": 1,
          "quote": 13800
        },
        {
          "laneId": 2,
          "quote": 15800
        },
        {
          "laneId": 3,
          "quote": 9200
        },
        {
          "laneId": 4,
          "quote": 8200
        },
        {
          "laneId": 5,
          "quote": 25800
        }
      ]
    },
    {
      "id": 103,
      "name": "Bharat Intercity Freight",
      "laneQuotes": [
        {
          "laneId": 1,
          "quote": 15200
        },
        {
          "laneId": 2,
          "quote": 17200
        },
        {
          "laneId": 3,
          "quote": 8800
        },
        {
          "laneId": 4,
          "quote": 8100
        },
        {
          "laneId": 5,
          "quote": 27200
        }
      ]
    },
    {
      "id": 104,
      "name": "SouthWest Cargo Lines",
      "laneQuotes": [
        {
          "laneId": 1,
          "quote": 14100
        },
        {
          "laneId": 2,
          "quote": 16100
        },
        {
          "laneId": 3,
          "quote": 9000
        },
        {
          "laneId": 4,
          "quote": 7600
        },
        {
          "laneId": 5,
          "quote": 26100
        }
      ]
    }
  ]
}
```

> **Sample data:** Transporter names and quote amounts are illustrative and are not real commercial quotes.

### Expected Behavior

- Accepts lane, transporter, and quote information.
- Persists the submitted information in PostgreSQL.
- Makes the stored data available to the other API endpoints.

---

## 2️⃣ Calculate Transporter Assignments

Calculates assignments based on the user-specified maximum number of distinct transporters.

### Request Details

**Method:** `POST`

**Endpoint:**

```text
/api/v1/transporters/assignment
```

**Full URL:**

```text
https://laneopt.onrender.com/api/v1/transporters/assignment
```

**Headers:**

```http
Content-Type: application/json
```

### Request Body

```json
{
  "maxTransporters": 2
}
```

### Optimization Objectives

The assignment process considers:

1. Complete coverage of the available lanes.
2. The maximum number of distinct transporters allowed.
3. Transportation cost optimization according to the implemented priorities.

The actual response contains the assignment result defined by the application's response DTO.

---

## 3️⃣ Retrieve All Lanes

Retrieves the lanes currently stored in the database.

**Method:** `GET`

**Endpoint:**

```text
/api/v1/lanes
```

**Full URL:**

```text
https://laneopt.onrender.com/api/v1/lanes
```

**Request Body:** None.

**Example Response:**

```json
[
  {
    "id": 1,
    "origin": "Bengaluru",
    "destination": "Pune",
    "description": "Bengaluru to Pune transportation lane"
  },
  {
    "id": 2,
    "origin": "Bengaluru",
    "destination": "Mumbai",
    "description": "Bengaluru to Mumbai transportation lane"
  }
]
```

The response properties depend on the fields exposed by the lane entity or response DTO.

---

## 4️⃣ Retrieve All Transporters

Retrieves transporter information stored in the database.

**Method:** `GET`

**Endpoint:**

```text
/api/v1/transporters
```

**Full URL:**

```text
https://laneopt.onrender.com/api/v1/transporters
```

**Request Body:** None.

**Example Response:**

```json
[
  {
    "id": 101,
    "name": "Namma Karnataka Logistics"
  },
  {
    "id": 102,
    "name": "Deccan Express Transport"
  }
]
```

---

## 5️⃣ Retrieve Quotes for a Specific Lane

Retrieves the quotes associated with a lane, including the cheapest quote and the available transporter quotes, according to the implemented response structure.

**Method:** `GET`

**Endpoint:**

```text
/api/v1/lanes/{laneId}/quotes
```

**Example URL — Bengaluru to Pune:**

```text
https://laneopt.onrender.com/api/v1/lanes/1/quotes
```

Replace `1` with the desired lane ID.

**Request Body:** None.

The response allows consumers to compare transporter prices for a specific route.

---

## 🧪 Testing the APIs

The APIs can be tested through Swagger UI or an API client such as Postman.

### Recommended Testing Sequence

1. Submit input using `POST /api/v1/transporters/input`.
2. Retrieve lanes using `GET /api/v1/lanes`.
3. Retrieve transporters using `GET /api/v1/transporters`.
4. Inspect quotes using `GET /api/v1/lanes/{laneId}/quotes`.
5. Execute the assignment using `POST /api/v1/transporters/assignment`.

This sequence demonstrates the complete workflow, from submitting input data to generating transporter assignments.

---

## 💻 Running Locally

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### 1. Clone the Repository

```bash
git clone https://github.com/ShreyashDesai09/laneopt
```

### 2. Navigate to the Project Directory

```bash
cd laneopt
```

If the repository contains a nested `laneopt` project directory, navigate into that directory before running Maven.

### 3. Configure the Database

Update:

```text
src/main/resources/application.properties
```

Configure the PostgreSQL connection and provide the database password through the `LANEOPT_DB_PASSWORD` environment variable.

Do not commit database passwords or other secrets to GitHub.

### 4. Build the Application

```bash
mvn clean package
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

### 6. Open Swagger UI

```text
http://localhost:8081/swagger-ui/index.html
```

The local application uses port `8081` by default, while the deployed service uses the port supplied by its hosting environment.

---

## 🐳 Docker and Deployment

LaneOpt is deployed on Render using Docker.

The deployment uses:

- A Docker-based Spring Boot application.
- A PostgreSQL database for persistent storage.
- Environment variables for database configuration.
- The hosting platform's assigned port.
- Swagger UI for public API documentation.

### Live Resources

- [Open Live Application](https://laneopt.onrender.com)
- [Open Swagger UI](https://laneopt.onrender.com/swagger-ui/index.html)
- [Watch Project Demonstration](https://www.loom.com/share/8cf8804e58794ed18dd84d9d203e81f0)

---

## 📁 Project Structure

The application follows a layered package structure.

```text
src/main/java/com/freightfox/laneopt/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
└── LaneoptApplication.java
```

The controller, DTO, entity, repository, and service packages separate API handling, data representation, persistence, and business logic.

The project also includes the application configuration, Maven build file, and Dockerfile.

---

## 📚 Key Design Considerations

- **Separation of Concerns:** Organizes application responsibilities into distinct layers.
- **Database Persistence:** Uses PostgreSQL to store lane, transporter, and quote information.
- **RESTful Design:** Exposes functionality through HTTP endpoints.
- **API Documentation:** Uses Swagger UI to make the APIs discoverable and testable.
- **Containerization:** Uses Docker to package the application for deployment.
- **Configuration Management:** Uses environment variables to configure the deployed application.

---

## 👨‍💻 About the Project

LaneOpt was developed as part of the **FreightFox Backend Assignment — Transporter Assignment on Lanes**.

The project demonstrates practical experience with Java backend development, Spring Boot, REST API design, relational database integration, and application deployment.

The live application and Swagger documentation are available through the links above.

---

<p align="center">
  <a href="https://laneopt.onrender.com">
    <img src="https://img.shields.io/badge/Explore%20LaneOpt-Live%20Application-16a34a?style=for-the-badge" alt="Explore LaneOpt">
  </a>
  <a href="https://laneopt.onrender.com/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/Try%20the%20APIs-Open%20Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Try the APIs">
  </a>
  <a href="https://www.loom.com/share/8cf8804e58794ed18dd84d9d203e81f0">
    <img src="https://img.shields.io/badge/Watch%20the%20Demo-Loom-625DF5?style=for-the-badge&logo=loom&logoColor=white" alt="Watch the Demo">
  </a>
</p>
