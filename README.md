##Database Design:
I create three tables - Customer, Merchant, and PaymentTransaction 


#Components:
Customer: Manages customer data .
Merchant: Handles merchant details and validation.
PaymentTransaction: Processes and tracks every transaction.


When we use only SQL fo creating database:(but i use hibernate to create database using springboot model with annotation @Table)

##Here is the SQL Satatements :
/*to create tables in sql statements*/

CREATE TABLE Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    dateOfRegistration DATE
);

CREATE TABLE Merchant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    active BOOLEAN
);

CREATE TABLE PaymentTransaction (
    id INT PRIMARY KEY AUTO_INCREMENT,
    transactionDate DATE,
    grossAmount DECIMAL(10, 2),
    VATRate ENUM('0%', '7%', '19%'),
    receiptId VARCHAR(255),
    customerId INT,
    FOREIGN KEY (customerId) REFERENCES Customer(id),
    merchantId INT,
    FOREIGN KEY (merchantId) REFERENCES Merchant(id)
);

##SQL Queries:

/*Different queries in sql in the task*/
/*a. To get the merchant with the highest turnover in 2022:*/
SELECT m.id, m.name, SUM(pt.grossAmount) as turnover
FROM Merchant m
JOIN PaymentTransaction pt ON m.id = pt.merchantId
WHERE YEAR(pt.transactionDate) = 2022
GROUP BY m.id, m.name
ORDER BY turnover DESC
LIMIT 1;

/*b. To check whether the merchant with the highest turnover is still active:*/
SELECT m.active
FROM Merchant m
WHERE m.id = (
    SELECT m.id
    FROM Merchant m
    JOIN PaymentTransaction pt ON m.id = pt.merchantId
    WHERE YEAR(pt.transactionDate) = 2022
    GROUP BY m.id
    ORDER BY SUM(pt.grossAmount) DESC
    LIMIT 1
);

/*c. Top 5 customers active in 2022 but not in 2023:*/
SELECT c.id, c.name, COUNT(pt.id) as transaction_count
FROM Customer c
JOIN PaymentTransaction pt ON c.id = pt.customerId
WHERE YEAR(pt.transactionDate) = 2022 AND c.id NOT IN (
    SELECT DISTINCT customerId
    FROM PaymentTransaction
    WHERE YEAR(transactionDate) = 2023
)
GROUP BY c.id, c.name
ORDER BY transaction_count DESC
LIMIT 5;


##Implementation Details :

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
