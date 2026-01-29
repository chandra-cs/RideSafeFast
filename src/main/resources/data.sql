-- Insert initial cab types (use INSERT IGNORE to avoid duplicates)
INSERT IGNORE INTO cab_types (name, base_fare, per_km_rate, description) VALUES
('Economy', 5.00, 1.50, 'Standard comfortable ride'),
('Premium', 8.00, 2.00, 'Luxury vehicle with premium amenities'),
('SUV', 12.00, 2.50, 'Large SUV for groups and families');

-- Insert sample customers (use INSERT IGNORE to avoid duplicates)
INSERT IGNORE INTO customers (name, email, phone, address, registration_date) VALUES
('John Doe', 'john.doe@email.com', '+1-555-0123', '123 Main St, New York, NY', '2025-10-01 10:00:00'),
('Jane Smith', 'jane.smith@email.com', '+1-555-0456', '456 Oak Ave, Los Angeles, CA', '2025-10-05 14:30:00'),
('Mike Johnson', 'mike.johnson@email.com', '+1-555-0789', '789 Pine Rd, Chicago, IL', '2025-10-08 09:15:00');