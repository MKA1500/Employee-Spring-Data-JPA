1. Create DB schema: src/main/resources/db/schema.sql
2. fill in the example data: src/main/resources/db/data.sql
3. This demo app can be tested with the Postman:

GET, "/api/employees" - Role("EMPLOYEE")

GET, "/api/employees/**" - Role("EMPLOYEE")

-> user: john, pass: test123

POST, "/api/employees" - Role("MANAGER")

PUT, "/api/employees/**" - Role("MANAGER")

-> user: jane, pass: test123

DELETE, "/api/employees/**" - Role("ADMIN")

-> user: maciej, pass: test123
