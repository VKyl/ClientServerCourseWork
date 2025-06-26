# InvestFolio - Investment Portfolio Management System

InvestFolio is a client-server application designed for tracking and managing investment portfolios. The system allows users to create accounts, add assets, record buy/sell transactions, and analyze the overall value and profitability of their portfolio.

## ✨ Key Features

* **User Authentication**: Secure registration and login using JWT (JSON Web Tokens).
* **Account Management**: Create, view, update, and delete investment accounts.
* **Asset Tracking**: Add various financial assets, such as stocks and cryptocurrencies.
* **Transaction Logging**: Record buy and sell operations, specifying quantity, price, and date.
* **Analytics Dashboard**: View a summary of the portfolio, including:
    * Total asset value.
    * Aggregate profit or loss.
    * Asset allocation by sector.
    * Detailed breakdown for each asset in the portfolio.
* **RESTful API**: A convenient and logically structured API for interacting with the server.

## 🛠️ Technology Stack

* **Language**: Java 21
* **Web Server**: Java's built-in `HttpServer`
* **Database**: MySQL (using Docker for deployment).
* **Build Tool**: Apache Maven
* **Dependencies**:
    * `Lombok`: To reduce boilerplate code.
    * `Jackson`: For JSON processing.
    * `java-jwt`: For creating and validating JWTs.
    * `dotenv-java`: For managing configuration variables.
* **Testing**: JUnit 5, Testcontainers, AssertJ.
* **CI/CD**: GitHub Actions for automated builds and testing.

## 🚀 Getting Started

### Prerequisites

* Java 22 or higher
* Maven
* Docker and Docker Compose

### Local Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/clientservercoursework.git](https://github.com/your-username/clientservercoursework.git)
    cd clientservercoursework
    ```

2.  **Configure environment variables:**
    Create a `.env` file in the root directory of the project, based on `env.sample` (if one exists) or with the following content:
    ```
    DB_HOST=localhost
    DB_NAME=investfolio_db
    DB_USER=user
    DB_PASS=password
    ```

3.  **Start MySQL with Docker Compose:**
    This command will run a MySQL database container in the background.
    ```bash
    docker-compose up -d mysqlDb
    ```
    *Note: Your `docker-compose.yml` specifies port "3307:3307". Make sure it is not occupied.*

4.  **Run the Java application:**
    You can run the server directly from your IDE by executing the `main` method in `org.example.Main`, or by building the project with Maven:
    ```bash
    mvn clean install
    java -jar target/CourseWork-1.0-SNAPSHOT.jar
    ```
    The server will start at `http://localhost:8080`.

## 🧪 Testing

The project includes a suite of integration tests to verify the logic of the services and database interactions. To run the tests, execute the command:
```bash
mvn test
```
