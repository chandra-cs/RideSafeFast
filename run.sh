#!/bin/bash

# Cab Booking System Startup Script

echo "🚀 Starting Cab Booking System..."
echo "================================="

# Check if MySQL is running
if ! command -v mysql &> /dev/null; then
    echo "❌ MySQL is not installed or not in PATH"
    echo "Please install MySQL and ensure it's running on port 3306"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed or not in PATH"
    echo "Please install Maven 3.6+ to build the project"
    exit 1
fi

# Check Java version
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed or not in PATH"
    echo "Please install Java 17+ to run the application"
    exit 1
fi

echo "✅ Checking Java version..."
java -version

echo "✅ Checking Maven version..."
mvn -version

echo "🔧 Building the project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi

echo "🧪 Running tests..."
mvn test

if [ $? -ne 0 ]; then
    echo "⚠️  Some tests failed, but continuing with startup..."
fi

echo "🚀 Starting Spring Boot application..."
echo "The application will be available at: http://localhost:8080"
echo "Database: MySQL on localhost:3306/cab_booking_db"
echo ""
echo "Press Ctrl+C to stop the application"
echo "================================="

mvn spring-boot:run