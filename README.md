
## Ongoing Project 
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

**23 October**

## Database Schema Overview

The project uses a relational database with the following main entities:

- **Users**: Stores user information including their full name, email, password, and the number of projects they manage.
- **Issues**: Represents issues or tasks related to a project, tracking title, status, priority, and tags.
- **Projects**: Contains project details like name, description, category, and associated tags.
- **Chat**: Represents group chats associated with users and projects.
- **Messages**: Stores the content of chat messages along with their timestamps.
- **Comments**: Holds comments on issues, tracking content and creation date.

### Relationships:
- Users are linked to Issues and Comments.
- Issues are linked to Projects and Comments.
- Chat is connected to Messages, Users, and Projects.


**24 October**

![Untitled](https://github.com/user-attachments/assets/0bc7d2b8-e035-4328-a8b0-9ce65eb2b3a1)


**25 October**


- **ChatService.java**: Updated the chat service interface to improve or modify chat-related functionalities.
- **ProjectService.java**: Enhanced the project service interface, adding new methods or improving existing ones.
- **UserService.java**: Updated the user service interface to better manage user-related functionalities.
- **ChatRepository.java**: Modified the chat repository to improve how chat data is retrieved and stored.
- **ProjectRepository.java**: Adjusted the project repository for better handling of project data.
- **ChatServiceImp.java**: Implemented changes to improve the chat service logic.
- **ProjectServiceImp.java**: Updated the project service implementation to fix issues or add enhancements.
- **UserServiceImp.java**: Enhanced the user service implementation, improving functionality and performance. 


