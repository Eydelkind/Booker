# API Automation Project — Restful Booker

## 📌 Description

This project is a sample implementation of automated API tests for the **Restful Booker** service:
https://restful-booker.herokuapp.com

The goal of the project is to practice API testing using:

* Java
* Rest Assured
* TestNG
* Maven

It demonstrates both basic and intermediate approaches to testing REST APIs, including working with JSON, DTOs, authentication, and full CRUD workflows.

---

## 🚀 Implemented Scenarios

The project covers the full CRUD lifecycle:

* **Create** — create a booking (`POST /booking`)
* **Read** — retrieve a booking (`GET /booking/{id}`)
* **Update** — full update (`PUT /booking/{id}`)
* **Partial Update** — partial update (`PATCH /booking/{id}`)
* **Delete** — delete a booking (`DELETE /booking/{id}`)

Authentication is also implemented:

* get token (`POST /auth`)

---

## 🧪 Main Test

Main test:

```text
fullBookingCrudTest
```

Scenario flow:

1. Create a booking
2. Validate response
3. Retrieve booking by ID
4. Validate data
5. Obtain authentication token
6. Perform full update (PUT)
7. Validate updated data
8. Perform partial update (PATCH)
9. Validate changes
10. Delete booking
11. Verify resource is no longer available (404)

---

## 🏗️ Project Structure

```text
src/test/java
  ├── tests
  │     └── BookingApiTest.java
  ├── models
  │     ├── Booking.java
  │     ├── BookingDates.java
  │     └── AuthRequest.java
  └── utils
        └── BookingDataFactory.java
```

### 📦 models

DTO classes used for serialization (Java → JSON)

### 🧪 tests

API test classes

### 🛠 utils

Test data factory

---

## ⚙️ Technologies Used

* Java 17+
* Rest Assured
* TestNG
* Jackson (JSON serialization)
* Maven

---

## ▶️ How to Run

### Using IntelliJ IDEA

* Open the project
* Navigate to `BookingApiTest`
* Run the test using the green run button

---

### Using Maven

```bash
mvn clean test
```

---

## 📌 Implementation Highlights

* DTOs are used for request body creation
* Jackson is used for serialization
* Request and response logging is enabled (`log().all()`)
* Test flow is split into logical steps (create / get / update / delete)
* Chaining is used (passing `bookingId` between requests)

---

## ⚠️ API Notes

This is a demo API with some limitations:

* data resets approximately every 10 minutes
* non-standard status codes (e.g., `DELETE → 201`)
* occasional instability

---

## 📚 What This Project Demonstrates

This project showcases:

* understanding of REST API and HTTP methods
* working with JSON and JSON Path
* building API tests with Rest Assured
* use of DTOs and serialization
* testing full resource lifecycle
* handling authentication

---

## 🔥 Possible Improvements

* extract common setup into `RequestSpecification`
* integrate Allure reports
* implement response deserialization into DTOs
* add parameterized tests (TestNG DataProvider)
* cover negative test scenarios

---

## 👤 Author

Lev Eydelkind
Junior QA Automation Engineer
