# LaneOpt — Freight Transportation Cost Optimization Engine

LaneOpt is a Java Spring Boot backend application that optimizes transporter assignments across logistics lanes while minimizing transportation costs.

The application accepts lane details and transporter quotes, then calculates an assignment that balances cost minimization, transporter utilization, and complete lane coverage.

## Objectives

- **Minimize transportation cost:** Find an assignment with the lowest possible total cost while satisfying the optimization objectives.
- **Maximize transporter utilization:** Use as many distinct transporters as possible, subject to the user-specified limit.
- **Ensure complete lane coverage:** Assign a valid transporter to every lane.
- **Persist input data:** Store lanes, transporters, and their quotes in PostgreSQL.
- **Provide REST APIs:** Expose endpoints for submitting input data and calculating optimized assignments.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot
- **Web:** Spring Web
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito
- **API Documentation:** Swagger UI / OpenAPI
- **Deployment:** Docker, Render

## Live Application

- **Base URL:** https://laneopt.onrender.com
- **Swagger UI:** https://laneopt.onrender.com/swagger-ui/index.html

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/v1/transporters/input` | Submit lanes, transporters, and their quotes |
| POST | `/api/v1/transporters/assignment` | Calculate optimized transporter assignments |
| GET | `/api/v1/lanes` | Retrieve all available lanes |
| GET | `/api/v1/transporters` | Retrieve all available transporters |
| GET | `/api/v1/lanes/{laneId}/quotes` | Retrieve transporter quotes for a lane, ordered by price |

## 1. Submit Transporter Input

This endpoint accepts lane information, transporter details, and their transportation quotes.

### Request

**Method:** `POST`

**URL — Local:**
```text
http://localhost:8081/api/v1/transporters/input
```

**URL — Deployed:**
```text
https://laneopt.onrender.com/api/v1/transporters/input
```

**Headers:**
```http
Content-Type: application/json
```

**Body:**

Select `Body → raw → JSON` in Postman.

Use the following example structure, adjusting the property names to match the input DTO exposed by your application:

```json
{
  "lanes": [
    {
      "id": 1,
      "origin": "Mumbai",
      "destination": "Pune"
    },
    {
      "id": 2,
      "origin": "Pune",
      "destination": "Bangalore"
    }
  ],
  "transporters": [
    {
      "id": 101,
      "name": "Transporter A"
    },
    {
      "id": 102,
      "name": "Transporter B"
    }
  ],
  "quotes": [
    {
      "transporterId": 101,
      "laneId": 1,
      "quote": 15000
    },
    {
      "transporterId": 102,
      "laneId": 1,
      "quote": 14000
    },
    {
      "transporterId": 101,
      "laneId": 2,
      "quote": 22000
    },
    {
      "transporterId": 102,
      "laneId": 2,
      "quote": 20000
    }
  ]
}
```

**Note:** Confirm the exact request property names against your input DTO or Swagger request schema before using this example. The JSON illustrates the intended data structure.

### Expected behavior

- Accepts lane and transporter information with associated quotes.
- Persists the input data in PostgreSQL.
- Makes the data available to the assignment endpoint.

---

## 2. Calculate Optimized Transporter Assignments

This endpoint calculates transporter assignments based on the maximum number of distinct transporters allowed.

### Request

**Method:** `POST`

**URL — Local:**
```text
http://localhost:8081/api/v1/transporters/assignment
```

**URL — Deployed:**
```text
https://laneopt.onrender.com/api/v1/transporters/assignment
```

**Headers:**
```http
Content-Type: application/json
```

**Body:**
```json
{
  "maxTransporters": 2
}
```

### Example result

For the sample input above, an optimal assignment using two distinct transporters is:

```json
{
  "status": "SUCCESS",
  "totalCost": 35000,
  "assignments": [
    {
      "laneId": 1,
      "transporterId": 102,
      "transporterName": "Transporter B",
      "quote": 14000
    },
    {
      "laneId": 2,
      "transporterId": 101,
      "transporterName": "Transporter A",
      "quote": 22000
    }
  ],
  "selectedTransporters": [
    101,
    102
  ]
}
```

**Note:** The exact response field names depend on your assignment response DTO. The example illustrates the expected result; verify it against your actual Swagger response schema.

The total cost is calculated as:

`14,000 + 22,000 = 36,000`

For this example, the assignment using Transporter B for both lanes would cost `14,000 + 20,000 = 34,000`, but it would use only one distinct transporter. If the optimization prioritizes maximizing transporter usage before minimizing cost, the two-transporter assignment is selected at a total cost of `36,000`.

---

## 3. Retrieve All Lanes

This endpoint retrieves the lanes currently stored in the database.

### Request

**Method:** `GET`

**URL — Local:**
```text
http://localhost:8081/api/v1/lanes
```

**URL — Deployed:**
```text
https://laneopt.onrender.com/api/v1/lanes
```

**Headers:** No special headers required.

**Body:** None.

### Example response

```json
[
  {
    "id": 1,
    "origin": "Mumbai",
    "destination": "Pune"
  },
  {
    "id": 2,
    "origin": "Pune",
    "destination": "Bangalore"
  }
]
```

---

## 4. Retrieve All Transporters

This endpoint retrieves all transporters currently stored in the database.

### Request

**Method:** `GET`

**URL — Local:**
```text
http://localhost:8081/api/v1/transporters
```

**URL — Deployed:**
```text
https://laneopt.onrender.com/api/v1/transporters
```

**Headers:** No special headers required.

**Body:** None.

### Example response

```json
[
  {
    "id": 101,
    "name": "Transporter A"
  },
  {
    "id": 102,
    "name": "Transporter B"
  }
]
```

---

## 5. Retrieve Quotes for a Specific Lane

This endpoint retrieves the transporter quotes associated with a lane, ordered from lowest to highest price.

### Request

**Method:** `GET`

**URL — Local:**
```text
http://localhost:8081/api/v1/lanes/1/quotes
```

**URL — Deployed:**
```text
https://laneopt.onrender.com/api/v1/lanes/1/quotes
```

Replace `1` with the ID of the lane you want to inspect.

**Headers:** No special headers required.

**Body:** None.

### Example response

```json
{
  "laneId": 1,
  "origin": "Mumbai",
  "destination": "Pune",
  "cheapestTransporter": {
    "transporterId": 102,
    "transporterName": "Transporter B",
    "quote": 14000
  },
  "allQuotes": [
    {
      "transporterId": 102,
      "transporterName": "Transporter B",
      "quote": 14000
    },
    {
      "transporterId": 101,
      "transporterName": "Transporter A",
      "quote": 15000
    }
  ]
}
```

This response makes it easy to identify the cheapest transporter while retaining all available quotes for comparison.

**Note:** Verify the response structure against your actual `LaneQuotesResponse` DTO.

---

## Project Structure

```text
src/main/java/com/freightfox/laneopt/
├── controller/
│   ├── TransporterController.java
│   ├── AssignmentController.java
│   └── LaneController.java
├── dto/
├── entity/
│   ├── Lane.java
│   ├── Transporter.java
│   └── TransporterQuote.java
├── repository/
│   ├── LaneRepository.java
│   ├── TransporterRepository.java
│   └── TransporterQuoteRepository.java
├── service/
│   ├── TransporterService.java
│   ├── AssignmentService.java
│   └── LaneService.java
└── LaneoptApplication.java
```

The structure above represents the intended organization. Keep only files and classes that actually exist in your repository.

## Running Locally

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Steps

1. Clone the repository:

   ```bash
   git clone <YOUR_GITHUB_REPOSITORY_URL>
   ```

2. Navigate to the Spring Boot project directory:

   ```bash
   cd laneopt
   ```

3. Configure your PostgreSQL connection in `src/main/resources/application.properties`.

4. Set the database password through the `LANEOPT_DB_PASSWORD` environment variable.

5. Build the application:

   ```bash
   mvn clean package
   ```

6. Run the application:

   ```bash
   mvn spring-boot:run
   ```

7. Open Swagger UI:

   ```text
   http://localhost:8081/swagger-ui/index.html
   ```

## Running Tests

Run the project's tests using:

```bash
mvn test
```

JUnit 5 and Mockito can be used to test the service layer and assignment logic.

## Docker Deployment

LaneOpt is deployed as a Docker-based Spring Boot application on Render.

The application uses environment variables for database configuration, allowing the same application build to run in different environments without hardcoding credentials.

## Design Considerations

- **Layered architecture:** Separates controllers, services, repositories, and entities.
- **Spring Data JPA:** Simplifies persistence and database interactions.
- **PostgreSQL:** Provides relational storage for lanes, transporters, and quotes.
- **REST APIs:** Exposes the application's functionality through HTTP endpoints.
- **Swagger/OpenAPI:** Provides interactive API documentation and testing.
- **Docker:** Packages the application for deployment.

## Author

Developed as a backend assignment demonstrating Java, Spring Boot, relational database integration, REST API development, and transportation cost optimization.
