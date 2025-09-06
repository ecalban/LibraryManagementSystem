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





