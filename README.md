# LaneOpt — Freight Transportation Cost Optimization Engine

LaneOpt is a Java Spring Boot backend application designed to optimize
transporter assignments across logistics lanes while minimizing total
transportation costs.

## Objectives

- Minimize total transportation cost.
- Maximize transporter usage up to a user-specified limit.
- Ensure every lane is assigned to a valid transporter.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- Swagger / OpenAPI

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/transporters/input` | Submit lanes, transporters, and quotes |
| POST | `/api/v1/transporters/assignment` | Retrieve optimized assignments |

## Project Structure

```text
src/main/java/com/freightfox/laneopt/
├── controller/
├── dto/
├── entity/
├── exception/
├── repository/
├── service/
└── LaneoptApplication.java
