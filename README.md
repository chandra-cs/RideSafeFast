# Cab Booking System - Spring Boot Microservices
# Author : Chandra Sekhar Jena

A comprehensive travel company cab booking management system built with Spring Boot, implementing microservices architecture with REST APIs, JUnit testing, and modern web technologies.

## 🚀 Project Overview

This project demonstrates a complete cab booking system for a travel company, featuring:
- **Microservices Architecture** with clear separation of concerns
- **RESTful Web Services** for all operations
- **Spring Boot Framework** with auto-configuration
- **JUnit Testing** with comprehensive test coverage
- **Responsive Web Interface** with Bootstrap
- **Database Integration** with JPA/Hibernate
- **Real-time Fare Calculator** with dynamic pricing

## 🏗️ Architecture

### Microservices Components:
1. **Customer Service** - Manage customer registrations and profiles
2. **Booking Service** - Handle cab bookings and reservations
3. **Fare Calculator Service** - Dynamic fare calculations
4. **Admin Service** - Administrative functions and reporting

### Technology Stack:
- **Backend**: Spring Boot 3.1.5, Spring Data JPA, Spring Web MVC
- **Database**: H2 (in-memory for demo), easily configurable for MySQL/PostgreSQL
- **Testing**: JUnit 5, Mockito, Spring Boot Test
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript ES6+
- **Build Tool**: Maven
- **Java Version**: 17+

## 📋 Features

### Customer Management:
- ✅ Customer registration and profile management
- ✅ Email and phone validation
- ✅ Customer search and lookup

### Booking System:
- ✅ Create new cab bookings
- ✅ View booking history
- ✅ Update booking status
- ✅ Cancel/confirm bookings
- ✅ Search bookings by location

### Fare Calculator:
- ✅ Dynamic fare calculation based on distance
- ✅ Cab type pricing (Economy, Premium, SUV)
- ✅ Surge pricing during peak hours
- ✅ Detailed fare breakdown

### Admin Dashboard:
- ✅ View all bookings and customers
- ✅ Booking status management
- ✅ Analytics and reporting
- ✅ Monthly revenue reports

## 🚀 Quick Start

### Prerequisites:
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (running on localhost:3306)

### Database Setup

1. Install MySQL and ensure it's running on port 3306
2. Create the database by running the setup script:
   ```bash
   mysql -u root -p < setup-mysql.sql
   ```
   Or manually create the database:
   ```sql
   CREATE DATABASE IF NOT EXISTS cab_booking_db;
   ```

3. Update database credentials in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=password
   ```

### Running the Application:

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd cab-booking-system
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Start the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - Main Application: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:cabdb`
     - Username: `sa`
     - Password: (leave empty)

## 📡 API Endpoints

### Customer Service (`/api/customers`)
- `POST /api/customers` - Register new customer
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Booking Service (`/api/bookings`)
- `POST /api/bookings` - Create new booking
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `GET /api/bookings/customer/{customerId}` - Get customer bookings
- `PUT /api/bookings/{id}/status` - Update booking status
- `PUT /api/bookings/{id}/cancel` - Cancel booking

### Fare Calculator (`/api/fare`)
- `POST /api/fare/calculate` - Calculate trip fare
- `POST /api/fare/detailed` - Get detailed fare breakdown
- `GET /api/fare/time-estimate` - Get estimated trip time

### Admin Service (`/api/admin`)
- `GET /api/admin/dashboard` - Get dashboard statistics
- `GET /api/admin/bookings` - Get all bookings (admin view)
- `PUT /api/admin/bookings/{id}/status` - Update booking status (admin)
- `GET /api/admin/reports/monthly` - Get monthly reports

## 🧪 Testing

The project includes comprehensive testing:

### Unit Tests:
- **Controller Tests**: MockMvc testing for REST endpoints
- **Service Tests**: Business logic testing with Mockito
- **Repository Tests**: Data layer testing

### Running Tests:
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BookingServiceTest

# Run with coverage
mvn test jacoco:report
```

## 🗄️ Database Schema

### Core Entities:
- **Customer**: User information and registration details
- **Booking**: Cab booking records with status tracking
- **CabType**: Different cab categories with pricing
- **Fare**: Detailed fare calculation records

### Sample Data:
The application initializes with sample data including:
- 3 cab types (Economy, Premium, SUV)
- 3 sample customers
- Realistic pricing structure

## 🔧 Configuration

### Application Properties:
```properties
# Server configuration
server.port=8080

# Database configuration
spring.datasource.url=jdbc:h2:mem:cabdb
spring.jpa.hibernate.ddl-auto=create-drop

# Enable H2 console for development
spring.h2.console.enabled=true
```

### Production Configuration:
For production, update `application.properties`:
```properties
# Use MySQL/PostgreSQL
spring.datasource.url=jdbc:mysql://localhost:3306/cabdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=validate
```

## 📊 Postman Testing

Import the provided API collection to test all endpoints:
1. Customer management operations
2. Booking creation and updates
3. Fare calculations
4. Admin dashboard functions

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/travelcompany/booking/
│   │   ├── controller/          # REST Controllers
│   │   ├── service/            # Business Logic Services
│   │   ├── model/              # JPA Entities
│   │   ├── repository/         # Data Access Layer
│   │   ├── dto/               # Data Transfer Objects
│   │   └── CabBookingApplication.java
│   └── resources/
│       ├── application.properties
│       ├── data.sql           # Initial data
│       ├── static/            # CSS, JS files
│       └── templates/         # Thymeleaf templates
└── test/
    └── java/com/travelcompany/booking/
        ├── controller/        # Controller tests
        └── service/          # Service tests
```

## 🔍 Key Design Patterns

1. **Repository Pattern**: Data access abstraction
2. **Service Layer Pattern**: Business logic separation  
3. **DTO Pattern**: Data transfer between layers
4. **MVC Pattern**: Web layer organization
5. **Dependency Injection**: Loose coupling with Spring IoC

## 🎯 Learning Objectives Achieved

✅ **Spring Boot Application Creation**: Complete project setup
✅ **Spring Web Dependency Configuration**: RESTful services
✅ **HTML Web Pages Development**: Responsive user interface  
✅ **Spring Controller Annotations**: @RestController, @RequestMapping
✅ **Microservices Implementation**: Booking and Fare services
✅ **JUnit Unit Testing**: Comprehensive test coverage
✅ **Postman API Testing**: RESTful endpoint validation
✅ **Frontend Integration**: Web form interactions
✅ **Maven Build Configuration**: Executable JAR creation

## 🚀 Future Enhancements

- [ ] Spring Security integration
- [ ] Real-time notifications with WebSocket
- [ ] Integration with external mapping APIs
- [ ] Payment gateway integration
- [ ] Mobile app support with JWT authentication
- [ ] Docker containerization
- [ ] Kubernetes deployment

## 👨‍💻 Development Guidelines

### Code Style:
- Follow Java naming conventions
- Use proper JavaDoc comments
- Implement proper error handling
- Write meaningful test cases

### Git Workflow:
- Feature branch development
- Pull request reviews
- Automated testing on CI/CD

## 📞 Support

For questions or issues:
1. Check the documentation
2. Review test cases for usage examples
3. Create an issue with detailed description

---

**Built with ❤️ using Spring Boot - Demonstrating enterprise-grade microservices architecture**
