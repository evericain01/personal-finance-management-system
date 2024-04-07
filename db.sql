-- Users Table
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Categories Table
CREATE TABLE categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

-- Budgets Table
CREATE TABLE budgets (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id),
  amount DECIMAL(10, 2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Expenses Table
CREATE TABLE expenses (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id),
  description TEXT NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  category_id INTEGER NOT NULL REFERENCES categories(id),
  date DATE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Reports Table 
CREATE TABLE reports (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id),
  filter_start_date DATE,
  filter_end_date DATE,
  generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data TEXT NOT NULL
);

INSERT INTO categories (name) VALUES 
('Groceries'),
('Utilities'),
('Transportation'),
('Entertainment'),
('Healthcare');

INSERT INTO expenses (user_id, description, amount, category_id, date, created_at) VALUES 
(1, 'Cheese and Crackers', 45.00, 1, CURRENT_DATE - interval '10 days', NOW()),
(1, 'Water Bill', 80.75, 2, CURRENT_DATE - interval '15 days', NOW()),
(1, 'Bus Pass', 50.00, 3, CURRENT_DATE - interval '20 days', NOW()),
(1, 'Book Purchase', 25.99, 4, CURRENT_DATE - interval '25 days', NOW()),
(1, 'Gym Membership', 75.00, 5, CURRENT_DATE - interval '30 days', NOW()),
(1, 'Coffee Beans', 30.00, 1, CURRENT_DATE - interval '35 days', NOW()),
(1, 'Heating Bill', 90.00, 2, CURRENT_DATE - interval '40 days', NOW()),
(1, 'Bicycle Repair', 65.50, 3, CURRENT_DATE - interval '45 days', NOW()),
(1, 'Music Album', 15.00, 4, CURRENT_DATE - interval '50 days', NOW()),
(1, 'Annual Checkup', 120.00, 5, CURRENT_DATE - interval '55 days', NOW()),
(2, 'Steak and Potatoes', 55.00, 1, CURRENT_DATE - interval '60 days', NOW()),
(2, 'Electricity Bill', 95.20, 2, CURRENT_DATE - interval '65 days', NOW()),
(2, 'Parking Ticket', 35.00, 3, CURRENT_DATE - interval '70 days', NOW()),
(2, 'Movie Streaming Subscription', 10.99, 4, CURRENT_DATE - interval '75 days', NOW()),
(2, 'Yoga Class', 40.00, 5, CURRENT_DATE - interval '80 days', NOW()),
(2, 'Fish and Chips', 20.00, 1, CURRENT_DATE - interval '85 days', NOW()),
(2, 'Mobile Phone Bill', 60.50, 2, CURRENT_DATE - interval '90 days', NOW()),
(2, 'Oil Change', 75.00, 3, CURRENT_DATE - interval '95 days', NOW()),
(2, 'Museum Donation', 50.00, 4, CURRENT_DATE - interval '100 days', NOW()),
(2, 'Vitamin Supplements', 30.25, 5, CURRENT_DATE - interval '105 days', NOW()),
(1, 'Organic Vegetables', 22.50, 1, CURRENT_DATE - interval '110 days', NOW()),
(1, 'Internet Bill', 65.00, 2, CURRENT_DATE - interval '120 days', NOW()),
(1, 'Metro Pass', 70.00, 3, CURRENT_DATE - interval '130 days', NOW()),
(1, 'Cinema Tickets', 40.00, 4, CURRENT_DATE - interval '140 days', NOW()),
(1, 'Dental Cleaning', 85.00, 5, CURRENT_DATE - interval '150 days', NOW()),
(2, 'Pasta and Sauce', 15.00, 1, CURRENT_DATE - interval '160 days', NOW()),
(2, 'Gas Bill', 50.25, 2, CURRENT_DATE - interval '170 days', NOW()),
(2, 'Train Pass', 80.00, 3, CURRENT_DATE - interval '180 days', NOW()),
(2, 'Concert Tickets', 75.00, 4, CURRENT_DATE - interval '190 days', NOW()),
(2, 'Eye Exam', 55.00, 5, CURRENT_DATE - interval '200 days', NOW()),
(1, 'Organic Eggs', 10.20, 1, CURRENT_DATE - interval '210 days', NOW()),
(1, 'Trash Service Fee', 30.45, 2, CURRENT_DATE - interval '220 days', NOW()),
(1, 'Car Rental', 120.00, 3, CURRENT_DATE - interval '230 days', NOW()),
(1, 'Video Game Purchase', 59.99, 4, CURRENT_DATE - interval '240 days', NOW()),
(1, 'Doctors Visit', 100.00, 5, CURRENT_DATE - interval '250 days', NOW()),
(2, 'Vegetable Stir Fry', 14.30, 1, CURRENT_DATE - interval '260 days', NOW()),
(2, 'Water Bill', 70.00, 2, CURRENT_DATE - interval '270 days', NOW()),
(2, 'Bus Fare', 25.00, 3, CURRENT_DATE - interval '280 days', NOW()),
(2, 'Art Supplies', 45.65, 4, CURRENT_DATE - interval '290 days', NOW()),
(2, 'Massage Therapy', 90.00, 5, CURRENT_DATE - interval '300 days', NOW()),
(1, 'Fresh Salmon', 35.50, 1, CURRENT_DATE - interval '15 days', NOW()),
(1, 'Gas Bill', 50.25, 2, CURRENT_DATE - interval '30 days', NOW()),
(1, 'Taxi to Airport', 40.00, 3, CURRENT_DATE - interval '45 days', NOW()),
(1, 'Theater Tickets', 60.00, 4, CURRENT_DATE - interval '60 days', NOW()),
(1, 'Prescription Medicine', 23.75, 5, CURRENT_DATE - interval '75 days', NOW()),
(2, 'Bakery Goods', 18.90, 1, CURRENT_DATE - interval '90 days', NOW()),
(2, 'Internet Bill', 45.00, 2, CURRENT_DATE - interval '105 days', NOW()),
(2, 'Subway Pass', 30.00, 3, CURRENT_DATE - interval '120 days', NOW()),
(2, 'Concert Tickets', 75.00, 4, CURRENT_DATE - interval '135 days', NOW()),
(2, 'Eye Exam', 55.00, 5, CURRENT_DATE - interval '150 days', NOW()),
(1, 'Organic Eggs', 10.20, 1, CURRENT_DATE - interval '165 days', NOW()),
(1, 'Trash Service Fee', 30.45, 2, CURRENT_DATE - interval '180 days', NOW()),
(1, 'Car Rental', 120.00, 3, CURRENT_DATE - interval '195 days', NOW()),
(1, 'Video Game Purchase', 59.99, 4, CURRENT_DATE - interval '210 days', NOW());

-- SELECT * FROM users
-- SELECT * FROM budgets
-- SELECT * FROM categories
-- SELECT * FROM expenses
-- SELECT * FROM reports

-- TRUNCATE TABLE users, budgets, categories, expenses, reports RESTART IDENTITY;