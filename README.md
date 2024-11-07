# Spring Boot Web Flux Products API <img src="https://spring.io/img/logos/spring-initializr.svg" alt="Spring Boot Logo" width="80" height="60"/>




* **This API is created for educational purposes.**
* **What exactly is this API?**
    * This API is essentially a CRUD API for managing products.
    * Each product has a name, price, and ID.
        * Prices can be exchanged in different currencies, depending on the request.
    * The API stores products in MongoDB Atlas.

* **It’s free to use! 😉**

---

# Dependencies Used

Here’s a list of the key dependencies used in this project:

| Dependency                                   | Version |
|----------------------------------------------|---|
| **Spring Boot Web Flux**                     | 3.3.3 |
| **Spring cloud config**                      | 2023.0.3  |
| **JDK LTS**                                  | 17 |
| **JUnit**                                    | 5 |
| **Lombok**                                   | 1.18 |

---
# Database Used

**This API originally works with MongoDB Atlas, but
you can configure  it to run with other
MongoDB servers.**

---

# How to Run

To run the API, simply enter the following command in your terminal:

```bash
./gradlew bootRun
```

