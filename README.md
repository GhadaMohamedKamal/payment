## Database Design:
I create three tables - Customer, Merchant, and PaymentTransaction 


## Components:
Customer: Manages customer data .
Merchant: Handles merchant details and validation.
PaymentTransaction: Processes and tracks every transaction.


When we use only SQL fo creating database:(but i use hibernate to create database using springboot model with annotation @Table)

## Here is the SQL Satatements :
## /*to create tables in sql statements*/

## CREATE TABLE Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    date_Of_Registration DATE
);

## CREATE TABLE Merchant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    active BOOLEAN
);

## CREATE TABLE Payment_Transaction (
    id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_Date_DATE,
    gross_Amount DECIMAL(10, 2),
    VATRate ENUM('0%', '7%', '19%'),
    receiptId VARCHAR(255),
    customer_Id INT,
    FOREIGN KEY (customer_Id) REFERENCES Customer(id),
    merchant_Id INT,
    FOREIGN KEY (merchant_Id) REFERENCES Merchant(id)
);
## Data Entry :/* Data entry for Customer table */
INSERT INTO Customer (name, email, date_Of_Registration) VALUES
('Ghada Mohd', 'ghadamohd@email.com', '2022-01-10'),
('Gabi Blanke', 'gabiblanke@email.com', '2022-02-12'),
('Mario Blanke', 'marioblanke@email.com', '2022-03-14');

## /* Data entry for Merchant table */
-- Using the previous merchants as no new merchants were provided
INSERT INTO Merchant (name, active) VALUES
('Merchant1', TRUE),
('Merchant2', FALSE),
('Merchant3', TRUE);


## /* Data entry for PaymentTransaction table */
-- Assuming Ghada Mohd made a transaction with Merchant1
INSERT INTO Payment_Transaction (transaction_Date, gross_Amount, VATRate, receipt_Id, customer_Id, merchant_Id) VALUES
('2022-04-01', 120.30, '7%', 'R20220401', 1, 1),  -- 1 is Ghada Mohd's ID, 1 is Merchant1's ID
-- Assuming Gabi Blanke made a transaction with Merchant3
('2022-04-15', 170.85, '19%', 'R20220415', 2, 3), -- 2 is Gabi Blanke's ID, 3 is Merchant3's ID
-- Assuming Mario Blanke made two transactions; one with Merchant1 and one with Merchant2
('2022-05-05', 45.00, '0%', 'R20220505A', 3, 1), -- 3 is Mario Blanke's ID, 1 is Merchant1's ID
('2022-05-20', 95.50, '7%', 'R20220520B', 3, 2); -- 3 is Mario Blanke's ID, 2 is Merchant2's ID


## SQL Queries:
## all sql statements in a file SQL-Queries.sql ..After pull the project  Please delete from project files before run  
## /*Different queries in sql in the task*/
## /*a. To get the merchant with the highest turnover in 2022:*/
JOIN Payment_Transaction pt ON m.id = pt.merchant_Id
WHERE YEAR(pt.transaction_Date) = 2022
GROUP BY m.id, m.name
ORDER BY turnover DESC
LIMIT 1;

##  Test on Heidi Result :
1,Me rchant One,450


## /*b. To check whether the merchant with the highest turnover is still active:*/

SELECT m.active
FROM Merchant m
WHERE m.id = (
    SELECT m.id
    FROM Merchant m
    JOIN Payment_Transaction pt ON m.id = pt.merchant_Id
    WHERE YEAR(pt.transaction_Date) = 2022
    GROUP BY m.id
    ORDER BY SUM(pt.gross_Amount) DESC
    LIMIT 1
);

## Result on heidi:
1,Active


## c. Top 5 customers active in 2022 but not in 2023:*/

SELECT c.id, c.name, COUNT(pt.id) as transaction_count
FROM Customer c
JOIN Payment_Transaction pt ON c.id = pt.customer_Id
WHERE YEAR(pt.transaction_Date) = 2022 AND c.id NOT IN (
    SELECT DISTINCT customer_Id
    FROM Payment_Transaction
    WHERE YEAR(transaction_Date) = 2023
)
GROUP BY c.id, c.name
ORDER BY transaction_count DESC
LIMIT 5;

## Result:
1,Ghada,6
2,Mario,2

## Implementation Details :

1. Spring Boot Project Setup
Use Spring Initializer (https://start.spring.io/) to create a new project with:

Project Type: Maven
Language: Java 17
Dependencies: Spring Web, Spring Data JPA and for database (maria DB)
Once downloaded,I extract the zip and open in Eclipse IDE.

2. Define Entities
Customer Entity , Merchant Entity , PaymentTransaction Entity
For filling entries in DB , I do it manually in Heidi SQL or by query in Heidi SQL like this :
-- Transactions for Ghada
INSERT INTO payment_Transaction (transaction_Date, gross_Amount, VATRate, receipt_Id, customer_id, merchant_id) VALUES ('2022-01-10', 100.0, 0.19, 'R12345', 1, 1);
INSERT INTO payment_Transaction (transaction_Date, gross_Amount, VATRate, receipt_Id, customer_id, merchant_id) VALUES ('2022-01-15', 50.0, 0.07, 'R12346', 1, 2);

3. Defining Repositories
For each entity,I define a repository interface

4. Defining Services & Implement API Endpoints 
 I assume that there are three services i included under MerchatService 
5. API Controller 

6. Test the controller endpoint by Postman

1. Get Merchant By ID:

GET http://localhost:8080/merchants/1

##Result in Postman:

{
    "name": "Merchant One",
    "active": true
}

2. Get All Active Merchants:

GET (http://localhost:8080/merchants/active)
Result in Postman:
[
    {
        "name": "Merchant One",
        "active": true
    },
    {
        "name": "Merchant Two",
        "active": true
    }
]


3. Get All Customers Without Transactions:

GET http://localhost:8080/customers/no-transactions
Result in Postman:
[
    {
        "name": "Blanke ",
        "email": "blanke@example.com",
        "dateOfRegistration": "2022-06-15"
    }
]
