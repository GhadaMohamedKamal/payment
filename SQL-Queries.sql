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

/*Different quesries in sql in the task*/
/*a. To get the merchant with the highest turnover in 2022:*/
JOIN Payment_Transaction pt ON m.id = pt.merchant_Id
WHERE YEAR(pt.transaction_Date) = 2022
GROUP BY m.id, m.name
ORDER BY turnover DESC
LIMIT 1;

/*b. To check whether the merchant with the highest turnover is still active:*/
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

/*c. Top 5 customers active in 2022 but not in 2023:*/
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