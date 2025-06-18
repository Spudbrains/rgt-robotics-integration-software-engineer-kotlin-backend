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
- **H2 Database**: In-memory database with console access
- **CORS Support**: Configured for frontend integration

## 🛠️ Technology Stack

- **Kotlin** - Programming language
- **Spring Boot 3.5.0** - Application framework
- **Spring Data JPA** - Data access layer
- **H2 Database** - In-memory database
- **Gradle** - Build tool (with wrapper)

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
- Java 21 or higher
- No additional tools required (Gradle wrapper included)

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

### Access the Application
- **API Base URL**: `http://localhost:3001/api/books`
- **H2 Database Console**: `http://localhost:3001/h2-console`
  - JDBC URL: `jdbc:h2:mem:bookstoredb`
  - Username: `sa`
  - Password: `password`

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

- **Server Port**: 3001
- **Database**: H2 in-memory database
- **JPA**: Hibernate with auto DDL
- **CORS**: Enabled for `http://localhost:3000`
- **Logging**: DEBUG level for application packages

## 🧪 Testing the API

### Using curl

1. **Get all books**:
   ```bash
   curl http://localhost:3001/api/books
   ```

2. **Search books**:
   ```bash
   curl "http://localhost:3001/api/books?search=gatsby&genre=Fiction"
   ```

3. **Create a book**:
   ```bash
   curl -X POST http://localhost:3001/api/books \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Test Book",
       "author": "Test Author",
       "isbn": "9781234567891",
       "price": 15.99,
       "stock": 10,
       "genre": "Fiction"
     }'
   ```

4. **Update stock**:
   ```bash
   curl -X PATCH http://localhost:3001/api/books/1/stock \
     -H "Content-Type: application/json" \
     -d '{"stock": 25}'
   ```

## 🔗 Frontend Integration

For frontend integration, set the following environment variable:

```bash
NEXT_PUBLIC_API_URL=http://localhost:3001/api
```

The API includes CORS configuration for `http://localhost:3000` to support frontend development.

## 🗄️ Database

The application uses H2 in-memory database with the following features:

- **Auto-creation**: Tables are created automatically on startup
- **Sample Data**: 5 sample books are loaded via DataInitializer component
- **Console Access**: Available at `/h2-console`
- **Reset on Restart**: Database is recreated each time the application starts

## 🚀 Deployment

To build the application for production:

```bash
# On Windows
.\gradlew.bat build

# On Unix/Linux/macOS
./gradlew build
```

The JAR file will be created in `build/libs/` directory.

## 🧹 Repository Cleanup

This repository has been cleaned up to include only essential files:

✅ **Kept**:
- Source code and configuration files
- Gradle wrapper (no need for global Gradle installation)
- Essential startup script
- Documentation

❌ **Removed**:
- Build artifacts (`build/` directory)
- Gradle cache (`.gradle/` directory)
- Large Gradle distribution zip
- Redundant batch files
- SQL data file (replaced with DataInitializer)

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

---

**Happy coding! 📚✨**