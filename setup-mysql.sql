-- MySQL Setup Script for Cab Booking System
-- Run this script in MySQL Workbench to set up the database

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS cab_booking_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE cab_booking_db;

-- Create a user for the application (optional - you can use root)
-- CREATE USER IF NOT EXISTS 'cab_user'@'localhost' IDENTIFIED BY 'cab_password';
-- GRANT ALL PRIVILEGES ON cab_booking_db.* TO 'cab_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Show that the database is created
SHOW DATABASES;

-- The Spring Boot application will automatically create the tables
-- when it starts up due to the JPA configuration

SELECT 'Database cab_booking_db created successfully!' as status;