# Library Management System (Java)

## Table of Contents
1. [Project Overview](#project-overview)  
2. [Features](#features)  
3. [Technologies Used](#technologies-used)  
4. [Installation](#installation)  
5. [Usage](#usage)  
6. [Database Setup](#database-setup)  
7. [Project Structure](#project-structure)  
8. [Screenshots](#screenshots)  
9. [Contributing](#contributing)  
10. [License](#license)  

---

## Project Overview
This project is a **Library Management System** developed in **Java** using **JavaFX** for the GUI and **PostgreSQL/MySQL** as the database.  
The system allows librarians to efficiently manage books, users, and borrowing activities.  

---

## Features
- **Librarian Login:** Secure authentication for librarians  
- **Book Management:** Add new books and search the catalog  
- **Librarian Management:** Register new librarians and update their profiles  
- **Student Management:** Add, search, and remove library members  
- **Borrowing & Returning:** Manage borrowing and returning of books, and track due dates  
- **Search Functionality:** Search for books by title, author, or category  

**Optional / Advanced Features:**  
- Tooltips and input validation in the GUI  
- Password reset functionality  

---

## Technologies Used
- **Programming Language:** Java 21 
- **GUI Framework:** JavaFX 21.0.8
- **Database:** PostgreSQL
- **Build Tool:** plain Java project
- **IDE:** Eclipse

---

## Installation

### Prerequisites
- JDK 21 or higher  
- JavaFX SDK 21.0.8  
- PostgreSQL  

### Steps
1. Clone the repository:  
```bash
git clone https://github.com/yourusername/LibraryManagementSystem.git
```
2.	Open the project in Eclipse.
3.	Add JavaFX libraries to the project:
	•	Right-click the project → Properties → Java Build Path → Libraries → Add External JARs
	•	Add the JavaFX JAR files (e.g., javafx-controls-21.0.8.jar, javafx-fxml-21.0.8.jar, etc.)
4.	Configure VM arguments for JavaFX:
	•	Right-click the project → Run As → Run Configurations → Arguments → VM Arguments
	•	Example:
```
--module-path "path_to_javafx_lib" --add-modules javafx.controls,javafx.fxml
```
5.	Configure your database connection:
	•	Open the class where the database connection is established (e.g., DBUtil.java or Main.java)
	•	Update username, password, and database URL according to your PostgreSQL setup.
6.	Run the project from Eclipse.

---

## Usage

1. Launch the application.  
2. Sign up as a librarian.  
3. Log in as a librarian.  
4. Use the menu to manage books, members, and borrowing/return operations.  
5. Quickly find books or members using the search features.  

> Note: These are the main operations, but the system also supports additional features.

---

## Database Setup

This project uses **PostgreSQL** as the database. Below are the steps to set up the required tables.

### 1. Create the database
First, create a database for the library system:
```sql
CREATE DATABASE LibraryManagementDB;
```
### 2. Create tables

Librarians Table
```sql
CREATE TABLE librarians (
    librarianid INTEGER NOT NULL PRIMARY KEY,
    librarianfullname VARCHAR(100),
    librarianphonenumber VARCHAR(50),
    librarianusername VARCHAR(50),
    librarianpassword VARCHAR(50),
    librarianstartdate DATE,
    librarianemail VARCHAR(100),
    isactive BOOLEAN DEFAULT FALSE
);
```
Students Table
```sql
CREATE TABLE students (
    studentid BIGINT NOT NULL PRIMARY KEY,
    studentfirstname VARCHAR(100),
    studentlastname VARCHAR(100),
    studentphonenumber VARCHAR(20),
    studentdepartment VARCHAR(100),
    studentemail VARCHAR(150),
    studentmembershipdate DATE,
    studentborrowedbooks BIGINT[],
    studentreturndate VARCHAR[]
);
```
Books Table
```sql
CREATE TABLE books (
    bookid INTEGER NOT NULL PRIMARY KEY,
    booktitle VARCHAR(255),
    bookauthor VARCHAR(255),
    bookdescription TEXT,
    bookcategory VARCHAR(100),
    bookstatus VARCHAR(20) CHECK (bookstatus IN ('Available', 'Unavailable')),
    bookstock INTEGER CHECK (bookstock >= 0),
    stockissued INTEGER CHECK (stockissued >= 0),
    bookswhoissued BIGINT[] DEFAULT '{}'
);
```
Remembered Users (Stores information of users who selected the “Remember Me” option during login.)
```sql
CREATE TABLE remembereduser (
    username VARCHAR(100) NOT NULL PRIMARY KEY
);
```
Students For Approvel (Stores students who have filled out the registration form and are pending approval.)
```sql
CREATE TABLE studentforapprove (
    studentid BIGINT NOT NULL PRIMARY KEY,
    studentfirstname VARCHAR(50),
    studentlastname VARCHAR(50),
    studentphonenumber VARCHAR(20),
    studentdepartment VARCHAR(100),
    studentemail VARCHAR(100)
);
```
## Tables Supporting GUI Components

Some tables are used to populate ComboBoxes and selection fields in the application.

•Book Categories
```sql
CREATE TABLE bookcategories (
    categoryname VARCHAR(100) NOT NULL PRIMARY KEY
);
```
•Country Codes
```sql
CREATE TABLE countrycodes (
    countrycode VARCHAR(10) NOT NULL PRIMARY KEY,
    phonecode VARCHAR(10)
);
```
•Departments
```sql
CREATE TABLE departments (
    departmentname VARCHAR(100) NOT NULL PRIMARY KEY
);
```

---

##Project Structure
LibraryManagementSystem/
├── .gitignore
├── README.md
├── LICENSE
├── module-info.java
├── src/
│   ├── application/
│   │   └── Main.java
│   ├── css/
│   │   └── style.css
│   ├── dao/
│   │   ├── BookDAO.java
│   │   ├── MemberDAO.java
│   │   └── BorrowDAO.java
│   ├── model/
│   │   ├── Book.java
│   │   ├── Member.java
│   │   └── Borrow.java
│   └── view/
│       ├── MainView.java
│       ├── BookView.java
│       └── MemberView.java
└── target/ (otomatik olarak oluşturulur)
























