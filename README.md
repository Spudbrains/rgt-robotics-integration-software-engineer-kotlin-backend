# Bookstore Backend API

A complete Spring Boot RESTful API built with Kotlin for online bookstore management. This backend provides comprehensive book management functionality including CRUD operations, search, pagination, sorting, and inventory management.

## 🚀 Features

- **Full CRUD Operations**: Create, read, update, and delete books
- **Advanced Search**: Search books by title or author with case-insensitive matching
- **Genre Filtering**: Filter books by genre
- **Pagination**: Efficient pagination with customizable page size
- **Sorting**: Sort books by any field in ascending or descending order
- **Inventory Management**: Track and update book stock levels
- **Sales Processing**: Process book sales with stock validation
- **PostgreSQL Database**: Production-ready database support
- **Docker Support**: Containerized deployment ready
- **CORS Support**: Configured for frontend integration

## 🛠️ Technology Stack

- **Kotlin 1.9.25** - Programming language
- **Spring Boot 3.5.0** - Application framework
- **Spring Data JPA** - Data access layer
- **PostgreSQL** - Production database
- **Gradle 8.5.0** - Build tool (with wrapper)
- **Docker** - Containerization

## 📁 Project Structure

```
bookstore-backend/
├── build.gradle.kts                 # Gradle build configuration
├── gradle.properties                # Gradle properties
├── gradlew                          # Gradle wrapper script (Unix)
├── gradlew.bat                      # Gradle wrapper script (Windows)
├── gradle/wrapper/                  # Gradle wrapper files
│   ├── gradle-wrapper.jar
│   └── gradle-wrapper.properties
├── start-app.bat                    # Windows startup script
├── Dockerfile                       # Docker container configuration
├── src/main/kotlin/com/example/bookstore/
│   ├── BookstoreApplication.kt      # Main application class
│   ├── DataInitializer.kt           # Database initialization
│   ├── controller/
│   │   └── BookController.kt        # REST API endpoints
│   ├── model/
│   │   └── Book.kt                  # Book entity model
│   ├── repository/
│   │   └── BookRepository.kt        # Data access layer
│   └── service/
│       └── BookService.kt           # Business logic layer
├── src/main/resources/
│   └── application.yml              # Application configuration
├── .gitignore                       # Git ignore rules
└── README.md                        # Project documentation
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- PostgreSQL database (for production)
- No additional tools required (Gradle wrapper included)

### Ubuntu 18.04 Fresh Installation Setup

If you're setting up this application on a fresh Ubuntu 18.04 system, follow these steps:

#### 1. Update System Packages
```bash
sudo apt update && sudo apt upgrade -y
```

#### 2. Install Java 17
```bash
# Add OpenJDK repository
sudo apt install -y software-properties-common
sudo add-apt-repository ppa:openjdk-r/ppa -y
sudo apt update

# Install OpenJDK 17
sudo apt install -y openjdk-17-jdk

# Verify installation
java -version
javac -version
```

#### 3. Set JAVA_HOME Environment Variable
```bash
# Add to your shell profile
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Verify JAVA_HOME
echo $JAVA_HOME
```

#### 4. Clone and Setup the Application
```bash
# Clone the repository (if not already done)
git clone https://github.com/Spudbrains/rgt-robotics-integration-software-engineer-kotlin-backend
cd rgt-robotics-integration-software-engineer-kotlin-backend

# Make Gradle wrapper executable
chmod +x gradlew
```

#### 5. Run the Application
```bash
# Using H2 database (recommended for development)
./gradlew bootRun

# Alternative: Build and run JAR
./gradlew build
java -jar build/libs/bookstore-0.0.1-SNAPSHOT.jar
```

#### 6. Verify the Application
```bash
# Check if the application is running
curl http://localhost:8080/actuator/health

# Test the API
curl http://localhost:8080/api/books

# Access H2 console
# Open browser: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:bookstoredb
# Username: sa
# Password: password
```

#### 7. Troubleshooting Common Issues

**Port 8080 already in use:**
```bash
# Find process using port 8080
sudo netstat -tulpn | grep :8080

# Kill the process
sudo kill -9 <PID>
```

**Permission denied on gradlew:**
```bash
chmod +x gradlew
```

**Java not found:**
```bash
# Reinstall Java
sudo apt install --reinstall openjdk-17-jdk
```

### Running the Application

#### Option 1: Using the provided script (Windows)
```bash
start-app.bat
```

#### Option 2: Using Gradle wrapper directly
```bash
# On Windows
.\gradlew.bat bootRun

# On Unix/Linux/macOS
./gradlew bootRun
```

#### Option 3: Using Java directly
```bash
# Build the application first
.\gradlew.bat build

# Then run the JAR
java -jar build/libs/bookstore-0.0.1-SNAPSHOT.jar
```

#### Option 4: Using Docker
```bash
# Build and run with Docker
docker build -t bookstore-backend .
docker run -p 8080:8080 bookstore-backend
```

### Access the Application
- **API Base URL**: `http://localhost:8080/api/books`
- **Health Check**: `http://localhost:8080/actuator/health`

## 📚 API Endpoints

### Get All Books
```http
GET /api/books?page=0&limit=10&search=gatsby&genre=Fiction&sortBy=title&sortOrder=asc
```

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `limit` (optional): Items per page (default: 10)
- `search` (optional): Search term for title or author
- `genre` (optional): Filter by genre
- `sortBy` (optional): Field to sort by (default: title)
- `sortOrder` (optional): asc or desc (default: asc)

**Response:**
```json
{
  "books": [...],
  "total": 100,
  "page": 0,
  "limit": 10,
  "totalPages": 10
}
```

### Get Book by ID
```http
GET /api/books/{id}
```

### Create Book
```http
POST /api/books
Content-Type: application/json

{
  "title": "New Book",
  "author": "Author Name",
  "isbn": "9781234567890",
  "price": 19.99,
  "stock": 50,
  "description": "Book description",
  "genre": "Fiction",
  "imageUrl": "https://example.com/image.jpg"
}
```

### Update Book
```http
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Updated Book Title",
  "author": "Updated Author",
  "isbn": "9781234567890",
  "price": 24.99,
  "stock": 45,
  "description": "Updated description",
  "genre": "Fiction",
  "imageUrl": "https://example.com/updated-image.jpg"
}
```

### Delete Book
```http
DELETE /api/books/{id}
```

### Update Stock
```http
PATCH /api/books/{id}/stock
Content-Type: application/json

{
  "stock": 30
}
```

### Sell Book
```http
POST /api/books/{id}/sell
Content-Type: application/json

{
  "quantity": 2
}
```

## 📊 Book Model

```kotlin
data class Book(
    val id: Long?,                    // Auto-generated ID
    var title: String,                // Book title
    var author: String,               // Author name
    var isbn: String,                 // Unique ISBN
    var price: Double,                // Book price
    var stock: Int,                   // Available stock
    var description: String?,         // Book description
    var publishedDate: String?,       // Publication date
    var genre: String?,               // Book genre
    var imageUrl: String?,            // Cover image URL
    val createdAt: LocalDateTime,     // Creation timestamp
    var updatedAt: LocalDateTime      // Last update timestamp
)
```

## 🔧 Configuration

The application is configured via `application.yml`:

- **Server Port**: 8080 (configurable via PORT environment variable)
- **Database**: PostgreSQL with environment variable configuration
- **JPA**: Hibernate with auto DDL update
- **Actuator**: Health and info endpoints enabled
- **Logging**: DEBUG level for application packages

## 🧪 Testing

### Running Tests

The application includes comprehensive tests that can be run using Gradle:

#### Run All Tests
```bash
# On Windows
.\gradlew.bat test

# On Unix/Linux/macOS
./gradlew test
```

#### Run Tests with Detailed Output
```bash
# On Windows
.\gradlew.bat test --info

# On Unix/Linux/macOS
./gradlew test --info
```

#### Run Specific Test Class
```bash
# On Windows
.\gradlew.bat test --tests BookControllerTest

# On Unix/Linux/macOS
./gradlew test --tests BookControllerTest
```

#### Run Tests with Coverage Report
```bash
# On Windows
.\gradlew.bat test jacocoTestReport

# On Unix/Linux/macOS
./gradlew test jacocoTestReport
```

#### Run Tests Continuously (Watch Mode)
```bash
# On Windows
.\gradlew.bat test --continuous

# On Unix/Linux/macOS
./gradlew test --continuous
```

### Test Structure

The test files are located in `src/test/kotlin/` and follow the same package structure as the main code:

```
src/test/kotlin/com/example/bookstore/
├── BookstoreApplicationTests.kt      # Main application tests
├── controller/
│   └── BookControllerTest.kt         # REST API endpoint tests
├── service/
│   └── BookServiceTest.kt            # Business logic tests
└── repository/
    └── BookRepositoryTest.kt         # Data access layer tests
```

### Test Types

#### Unit Tests
- **Service Layer**: Tests business logic without database
- **Repository Layer**: Tests data access with in-memory database
- **Controller Layer**: Tests REST endpoints with mocked services

#### Integration Tests
- **End-to-End**: Tests complete request/response flow
- **Database Integration**: Tests with real database operations

#### Test Configuration
Tests use:
- **H2 Database**: In-memory database for testing
- **Test Containers**: For integration tests (if configured)
- **MockMvc**: For testing REST endpoints
- **@DataJpaTest**: For repository tests
- **@WebMvcTest**: For controller tests

### Test Examples

#### Running Controller Tests
```bash
# Test only controller layer
.\gradlew.bat test --tests "*ControllerTest"
```

#### Running Service Tests
```bash
# Test only service layer
.\gradlew.bat test --tests "*ServiceTest"
```

#### Running Repository Tests
```bash
# Test only repository layer
.\gradlew.bat test --tests "*RepositoryTest"
```

### Test Reports

After running tests, you can find:
- **Test Results**: `build/test-results/`
- **Test Reports**: `build/reports/tests/`
- **Coverage Reports**: `build/reports/jacoco/` (if using JaCoCo)

### Troubleshooting Tests

#### Common Issues

**Tests failing due to database connection:**
```bash
# Clean and rebuild
.\gradlew.bat clean test
```

**Out of memory during tests:**
```bash
# Increase memory for tests
.\gradlew.bat test -Dorg.gradle.jvmargs="-Xmx2g"
```

**Port conflicts during integration tests:**
```bash
# Use random ports
.\gradlew.bat test -Dspring.test.port=0
```

### Continuous Integration

For CI/CD pipelines, use:
```bash
# Run tests and build
.\gradlew.bat clean build

# Run tests only
.\gradlew.bat test

# Run tests with coverage
.\gradlew.bat test jacocoTestReport
```

### Environment Setup

## 🔗 Frontend Integration

For frontend integration, set the following environment variable:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

The API includes CORS configuration for `http://localhost:3000` to support frontend development.

## 🗄️ Database

The application uses PostgreSQL with the following features:

- **Auto-creation**: Tables are created automatically on startup
- **Sample Data**: 5 sample books are loaded via DataInitializer component
- **Environment Configuration**: Database connection via environment variables
- **Production Ready**: Suitable for production deployment

## 🚀 Deployment

### Local Build
```bash
# On Windows
.\gradlew.bat build

# On Unix/Linux/macOS
./gradlew build
```

The JAR file will be created in `build/libs/` directory.

### Docker Deployment
```bash
# Build Docker image
docker build -t bookstore-backend .

# Run container
docker run -p 8080:8080 bookstore-backend
```

### Railway Deployment
The application is configured for Railway deployment with:
- Environment variable support for database configuration
- PORT environment variable for server port
- Docker containerization ready