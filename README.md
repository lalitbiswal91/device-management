## 📱 Device Management Service

- This project is a Spring Boot application for managing a device database.

## ✨ Features

- ➕ Add a new device
- 🔍 Get a device by id
- 📋 List all devices
- 🔄 Update device (partial & full)
- ❌ Delete device
- 🔎 Search device by brand

## Technologies

- **Spring Boot**
- **H2 Database**
- **Mockito**
- **Docker**
- **JaCoCo**
- **Swagger**

## 🚀 Getting Started

### Software Needed

- **Java 17**
- **Maven**
- **Docker (Optional)**

### Installation

**Clone the application from below GIT link**

```bash
https://github.com/lalitbiswal91/device-management.git
```

**2. Build the project using Maven:**
```sh
mvn clean install
```

### Running the Application

**Using Maven**

```sh
mvn spring-boot:run
```

**Using Docker Compose**

```sh
docker-compose up --build
```

## 📚 Device Management Service API Endpoints

### ➕ Add Device (**POST**)

```http
POST /api/devices/add-device
```

**Request Body**
```json
{
  "name": "iPad",
  "brand": "Apple"
}
```

### 🔍 Get Device by id (**GET**)

```http
GET /api/devices/{id}
```

### 📋 List All Devices (**GET**)

```http
GET /api/devices/all-devices
```

### 🔄 Update Device (**PUT**)

```http
PUT /api/devices/{id}
```

**Request Body**
```json
{
  "name": "New-Device",
  "brand": "New-Brand-Name"
}
```

### ❌ Delete Device (**DELETE**)

```http
DELETE /api/devices/{id}
```

### 🔎 Search Devices by Brand (**GET**)

```http
GET /api/devices/search?brand=Apple
```

## Running Tests

To run the tests, use the below maven command:

```sh
mvn test
```

### Get Test Coverage

To generate JaCoCo test coverage, run the below maven command:

```sh
mvn jacoco:report
```

## 📖 Swagger Documentation and accessing the API Urls

Open swagger ui at port 8085 if you are running the project using maven:

```http
http://localhost:8085/swagger-ui.html
```
Open swagger ui at port 9292 if you are running the project using docker compose:

```http
http://localhost:9292/swagger-ui.html
```

## 🗃️ Access H2 Database

No password will be required to log in to the db.

```http
http://localhost:8085/h2-console/
```




