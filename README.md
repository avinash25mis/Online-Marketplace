# 🛒 Online Marketplace — Backend

An e-commerce backend built with Java and Spring Boot, following a **microservices architecture**. The system consists of three independent services that together power a complete online shopping experience: browsing products, managing a shopping basket, and processing payments.

---

## 🧭 Table of Contents

- [What Does It Do? (Non-Technical Overview)](#-what-does-it-do-non-technical-overview)
- [Architecture Overview](#-architecture-overview)
- [Services](#-services)
  - [Products Service](#1-products-service)
  - [Baskets Service](#2-baskets-service-shopping-cart)
  - [Payments Service](#3-payments-service)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Run with Docker Compose](#run-with-docker-compose)
  - [Run Individual Services Locally](#run-individual-services-locally)
- [API Reference](#-api-reference)
  - [Products API](#products-api--port-8081)
  - [Baskets API](#baskets-api--port-8083)
  - [Payments API](#payments-api--port-8082)
- [Promotions & Pricing](#-promotions--pricing)
- [Mock Data](#-mock-data)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [User Stories](#-user-stories)

---

## 🗣️ What Does It Do? (Non-Technical Overview)

This is the **backend engine** of an online marketplace — think of it as the invisible machinery running behind an online shop. It handles three core things:

1. **Product Catalog** — Stores information about products: their name, price, and any deals/discounts currently active on them.

2. **Shopping Basket (Cart)** — Lets customers add or remove products from their basket, just like placing items in a supermarket trolley. Each customer has their own basket identified by their Customer ID.

3. **Checkout & Payment** — When a customer is ready to buy, the system calculates the total (applying any active promotions), then sends the payment request to the Payment service. If payment succeeds, the basket is cleared. If it fails, the basket is preserved so the customer can try again.

**Supported Promotions:**
- 🏷️ **2-for-1**: Buy 2 items, pay for 1. (e.g., 4 items → charged for 2)
- 🏷️ **3-for-2**: Buy 3 items, pay for 2. (e.g., 6 items → charged for 4)

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                     Client / Frontend                   │
└───────────────┬─────────────────┬───────────────────────┘
                │                 │
                ▼                 ▼
   ┌────────────────────┐  ┌──────────────────────┐
   │  Baskets Service   │  │   Products Service   │
   │   (Port 8083)      │◄─│    (Port 8081)       │
   └────────┬───────────┘  └──────────────────────┘
            │
            ▼
   ┌──────────────────────┐
   │   Payments Service   │
   │    (Port 8082)       │
   └──────────────────────┘
```

- The **Baskets Service** is the central coordinator — it calls the Products Service to fetch product prices and promotions, and the Payments Service to process checkout.
- Each service runs as an independent Docker container and can be scaled separately.
- All services expose a **Swagger/OpenAPI UI** for easy API exploration.

---

## 🔧 Services

### 1. Products Service
> **Port:** `8081` | **Package:** `com.wayfair.products`

A stub microservice providing product data (ID, price, active promotions). In production this would be backed by a real product database.

**Product ID Rules (stub behavior):**
| ID Prefix | Behavior |
|-----------|----------|
| `WF0123`, `WF01234`, `WF012345` | Hardcoded product data |
| Starts with `WF1` | Random product, no promotions, price up to £2000 |
| Starts with `WF2` | Random product with **2-for-1** promotion, price up to £1000 |
| Starts with `WF3` | Random product with **3-for-2** promotion, price up to £100 |
| Any other prefix | Product not found |

---

### 2. Baskets Service (Shopping Cart)
> **Port:** `8083` | **Package:** `com.mayfair.baskets`

The core service managing customer shopping baskets. Supports:
- Adding a product to a customer's basket
- Removing a product from a customer's basket
- Listing all products in a customer's basket
- Getting the total basket value (with promotions applied)
- Checking out (submitting payment)

Data is stored **in-memory** (no database required to run). The basket state is thread-safe.

---

### 3. Payments Service
> **Port:** `8082` | **Package:** `com.wayfair.payments`

A stub payment processor. Accepts a basket ID and payment value, returns a payment ID asynchronously.

**Stub behavior:**
- Returns a random payment ID for most requests ✅
- Returns an error if `basketId` starts with `eee` ❌
- Returns an error if the same basket ID is submitted more than once ❌

---

## 🚀 Getting Started

### Prerequisites

| Tool | Version |
|------|---------|
| Java | 11+ |
| Gradle | 6.3+ (or use the included `gradlew` wrapper) |
| Docker | Any recent version |
| Docker Compose | v3.4+ |

---

### Run with Docker Compose

The easiest way to start all three services at once:

```bash
# Build and start all services
docker-compose up --build

# Or build/start individual services
docker-compose build products
docker-compose up products

docker-compose build payments
docker-compose up payments

docker-compose build baskets
docker-compose up baskets
```

Once running, the services will be available at:
- Products Service: http://localhost:8081/swagger
- Payments Service: http://localhost:8082/swagger
- Baskets Service:  http://localhost:8083/swagger

---

### Run Individual Services Locally

Each service can be run independently using the Gradle wrapper:

```bash
# Products Service
cd products-service
./gradlew bootRun

# Payments Service
cd payments-service
./gradlew bootRun

# Baskets Service
cd baskets-service
./gradlew bootRun
```

**Run tests:**
```bash
./gradlew test
```

---

## 📡 API Reference

All APIs are also documented interactively via Swagger UI at `http://localhost:<port>/swagger`.

---

### Products API — Port `8081`

#### Get a Product by ID
```http
GET /api/v1/products/{id}
```
**Example:**
```bash
curl http://localhost:8081/api/v1/products/WF0123
```
**Response:**
```json
{
  "id": "WF0123",
  "price": 29.99,
  "promotions": ["3for2"]
}
```

---

### Baskets API — Port `8083`

#### ➕ Add a Product to Basket
```http
POST /api/v1/baskets
Content-Type: application/json
```
```bash
curl -X POST http://localhost:8083/api/v1/baskets \
  -H "Content-Type: application/json" \
  -d '{"customerId": "5001", "productId": "WF0123"}'
```

#### ➖ Remove a Product from Basket
```http
DELETE /api/v1/baskets
Content-Type: application/json
```
```bash
curl -X DELETE http://localhost:8083/api/v1/baskets \
  -H "Content-Type: application/json" \
  -d '{"customerId": "5001", "productId": "WF0123"}'
```

#### 📋 Get All Products in a Basket
```http
GET /api/v1/baskets/customer/{customerId}
```
```bash
curl http://localhost:8083/api/v1/baskets/customer/5001
```
**Response:**
```json
{
  "status": "OK",
  "message": "Product List",
  "data": ["WF0123", "WF01234"]
}
```

---

### Payments API — Port `8082`

#### 💳 Submit a Payment
```http
POST /api/v1/payments
Content-Type: application/json
```
```bash
curl -X POST http://localhost:8082/api/v1/payments \
  -H "Content-Type: application/json" \
  -d '{"basketId": "5001", "paymentValue": 59.98}'
```
**Response:**
```json
{
  "basketId": "5001",
  "paymentId": "PAY-abc123xyz"
}
```

---

## 🏷️ Promotions & Pricing

The basket service automatically applies the best available promotion when calculating totals:

| Promotion | Rule | Example |
|-----------|------|---------|
| **2-for-1** | Every 2 items, pay for 1 | 4 items → charged for 2 |
| **3-for-2** | Every 3 items, pay for 2 | 6 items → charged for 4 |

> If a product has both promotions, one is chosen automatically.

---

## 🗃️ Mock Data

Since the services run without a database, the following test data is available out of the box:

| Type | Available Values |
|------|-----------------|
| **Product IDs** | `WF0123`, `WF01234`, `WF012345` |
| **Customer IDs** | `5001`, `5002`, `5003` |

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 11** | Core programming language |
| **Spring Boot 2.3.1** | Application framework |
| **Spring Web** | REST API development |
| **Spring Actuator** | Health checks and monitoring |
| **Spring Validation** | Request validation |
| **Lombok** | Boilerplate reduction (getters, setters, logging) |
| **SpringDoc OpenAPI (Swagger)** | Interactive API documentation |
| **Gradle** | Build automation |
| **Docker / Docker Compose** | Containerisation and orchestration |
| **JUnit 5** | Unit and integration testing |

---

## 📁 Project Structure

```
Online-Marketplace/
├── docker-compose.yaml          # Orchestrates all three services
├── lombok.config                # Lombok configuration
├── products-service/            # Product catalog microservice (port 8081)
│   ├── src/main/java/com/wayfair/products/
│   │   ├── ProductsServiceMain.java
│   │   ├── ProductController.java
│   │   ├── ProductService.java
│   │   └── Product.java
│   └── Dockerfile
├── baskets-service/             # Shopping basket microservice (port 8083)
│   ├── src/main/java/com/mayfair/baskets/
│   │   ├── BasketsServiceMain.java
│   │   ├── controller/BasketController.java
│   │   ├── service/BasketService.java
│   │   ├── service/BasketValidationService.java
│   │   ├── repository/BasketRepository.java
│   │   ├── model/Basket.java
│   │   ├── dto/                 # Request/response DTOs
│   │   ├── exceptions/          # Custom exception handling
│   │   ├── constants/           # API and app constants
│   │   └── cache/BsAppCache.java
│   └── Dockerfile
├── payments-service/            # Payment processor microservice (port 8082)
│   ├── src/main/java/com/wayfair/payments/
│   │   ├── PaymentsServiceMain.java
│   │   ├── PaymentController.java
│   │   ├── PaymentService.java
│   │   ├── PaymentException.java
│   │   └── ExceptionControllerAdvice.java
│   └── Dockerfile
└── User stories/                # Feature requirements and acceptance criteria
    ├── user-story-1.md          # Add products to basket
    ├── user-story-2.md          # Remove products from basket
    ├── user-story-3.md          # Calculate basket total with promotions
    └── user-story-4.md          # Checkout and payment
```

---

## 📖 User Stories

| Story | Feature | Status |
|-------|---------|--------|
| [User Story 1](User%20stories/user-story-1.md) | Add products to basket | ✅ |
| [User Story 2](User%20stories/user-story-2.md) | Remove products from basket | ✅ |
| [User Story 3](User%20stories/user-story-3.md) | Calculate total with promotions | ✅ |
| [User Story 4](User%20stories/user-story-4.md) | Checkout & payment integration | ✅ |
