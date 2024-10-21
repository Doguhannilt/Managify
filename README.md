

### Daily Project Updates:

**20 October:**

- Initialized the project using Spring Initializer.
- Added the PostgreSQL driver to the project.

  ```
  spring.application.name=managify
  
  spring.datasource.url=
  spring.datasource.username=
  spring.datasource.password=
  spring.datasource.driver-class-name=
  
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
  ```

- Successfully created the "Managify" database on the local machine.
- Custom security config created and Jsonwebtoken added.

**21 October**

- Created User Model:*
   - Defined a `User` entity using JPA annotations. 
   - Added fields such as `id`, `fullName`, `email`, `password`, and `projectSize`.
   - Implemented a one-to-many relationship with the `Issues` entity using `@OneToMany`.

- Created AuthController:
   - Implemented an `AuthController` class to handle signup and signin processes.
   - Used `UserRepository` for database operations and `PasswordEncoder` to hash passwords.
   - Created a signup endpoint that checks for existing users, saves new users, and generates JWT tokens for authentication.

- Implemented Custom Authentication Logic:
   - Added a method to manually authenticate users by comparing provided credentials with the stored credentials using `UserDetailsService` and `PasswordEncoder`.

- JWT Generation:
   - Attempted to create a JWT token using `io.jsonwebtoken` library, but encountered a `WeakKeyException` because the key used for signing the JWT was not strong enough (only 200 bits).

- Fixed JWT Key Issue:
   - Updated the `JwtProvider` to use `Keys.secretKeyFor(SignatureAlgorithm.HS256)` to generate a 256-bit secure key, resolving the weak key issue.

**22 October**

- Database Issue in User Model:
   - Encountered a PostgreSQL error due to the relation "users" not existing. Fixed this by adding a `@Table(name = "users")` annotation to ensure the correct table name in the database.

- Password Matching Issue:
   - Experienced an issue where the `matches` method in `PasswordEncoder` wasn't working as expected during the sign-in process.
   - Resolved the problem by ensuring proper use of `passwordEncoder.matches()` instead of calling the method statically.

- Resolved PostgreSQL Column Issue:
   - Encountered an error related to a missing column (`id`) in a query. Investigated and fixed this issue by ensuring the column mapping was correct and the table structure matched expectations.

- Final JWT Integration:
   - Successfully integrated JWT-based authentication, allowing the application to generate tokens during signup and signin, and store them securely.


