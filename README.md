# Dairy Management System

## Overview

The **Dairy Management System** is a Java Fullstack web application designed to efficiently manage dairy operations, including product stock management, billing, wholesaler management, and sales tracking. Built with Spring Boot, Thymeleaf, MySQL, and JavaScript, the system provides an intuitive interface for managing dairy products, customer billing, wholesaler assignments, and sales records.

## Features

* **Stock Module**

  * Manage company-wise stock, available stock, and returns for sold products.
  * Real-time stock updates with return functionality and validation.

* **Sell Module**

  * Track daily and weekly sales history.
  * Customer-type-based billing (Customer, Wholesaler, Retailer).
  * CGST, SGST, and discount calculations.
  * Dynamic product rows with automatic total calculations.

* **Billing Module**

  * Generate bills with GST and discount calculations.
  * Auto-fetch the next bill number during bill creation.
  * Print-friendly bill format.

* **Wholesaler Module**

  * Manage wholesalers and assign products dynamically through popup forms.
  * Handle product returns and adjust stock levels accordingly.

* **Payment Module**

  * Add, edit, and search payment history.
  * Filter payments by date range and ID.

* **Other Expenses Module**

  * Track expenses like light bills, diesel costs, shop rent, and miscellaneous expenses.

* **Admin Module**

  * Single-user login with a default admin account.
  * 'Forgot Password' feature with email verification and token-based password reset.

## Technologies Used

* **Backend:** Java, Spring Boot, Spring Data JPA
* **Frontend:** Thymeleaf, HTML, CSS, JavaScript, Bootstrap
* **Database:** MySQL
* **Build Tool:** Maven
* **Version Control:** Git

## Project Structure

```
📦 dairyManagement
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.dairy.management
│   │   │       ├── controllers
│   │   │       ├── models
│   │   │       ├── repositories
│   │   │       └── services
│   │   ├── resources
│   │   │   ├── templates
│   │   │   │   ├── *.html
│   │   │   └── application.properties
│   └── test
│
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

* Java 17 or later
* MySQL Server
* Maven 3.8+
* IDE (IntelliJ, Eclipse, or VSCode)

### Setup

1. Clone the repository:

   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory:

   ```bash
   cd dairyManagement
   ```

3. Configure the database in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/dairy_management
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.thymeleaf.cache=false
   ```

4. Build the project:

   ```bash
   mvn clean install
   ```

5. Run the application:

   ```bash
   mvn spring-boot:run
   ```

6. Access the application:

   ```
   ```

[http://localhost:8080](http://localhost:8080)

```

## Default Login Credentials
- Username: `pranavjejurkar5019@gmail.com`
- Password: `admin`

## Contributing
1. Fork the project
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- Spring Boot Documentation
- Thymeleaf Documentation
- MySQL Documentation
- Bootstrap Documentation

```
