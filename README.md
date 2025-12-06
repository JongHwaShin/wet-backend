# Wet Backend API

Spring Boot REST API backend for the Wet Flutter application with MariaDB integration.

## Prerequisites

- Java 17 or higher
- MariaDB server running
- Gradle (wrapper included)

## Configuration

Before running the application, update the MariaDB connection details in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/wetdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

### Using Gradle Wrapper (Recommended)

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The server will start on `http://localhost:8080`

## API Endpoints

### Health Check
- **GET** `/api/health` - Check if the API is running

### User Management
- **GET** `/api/users` - Get all users
- **GET** `/api/users/{id}` - Get user by ID
- **POST** `/api/users` - Create a new user
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com"
  }
  ```
- **PUT** `/api/users/{id}` - Update user
- **DELETE** `/api/users/{id}` - Delete user

## Testing the API

### Using curl

```bash
# Health check
curl http://localhost:8080/api/health

# Get all users
curl http://localhost:8080/api/users

# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com"}'
```

## Project Structure

```
wet-backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/wetbackend/
│   │   │   ├── WetBackendApplication.java    # Main application class
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java            # CORS configuration
│   │   │   ├── controller/
│   │   │   │   └── UserController.java       # REST endpoints
│   │   │   ├── model/
│   │   │   │   └── User.java                 # User entity
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java       # Data access layer
│   │   │   └── service/
│   │   │       └── UserService.java          # Business logic
│   │   └── resources/
│   │       └── application.properties        # Configuration
│   └── test/
├── build.gradle                              # Gradle build configuration
└── gradlew                                   # Gradle wrapper script
```

## CORS Configuration

The API is configured to accept requests from any origin for development purposes. In production, you should restrict this to your Flutter app's domain in `WebConfig.java`.

## Database

The application uses JPA with Hibernate to automatically create/update the database schema. The `users` table will be created automatically when you first run the application.

## Troubleshooting

### Connection Issues
- Ensure MariaDB is running
- Verify database credentials in `application.properties`
- Check if the database exists (create it if needed: `CREATE DATABASE wetdb;`)

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```
