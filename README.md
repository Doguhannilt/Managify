
## Ongoing Project  
### Daily Project Updates  

### 20 October  
- **Project Initialization**: Set up the project using Spring Initializer and added the PostgreSQL driver.
- **Database Configuration**:
  - Configured `application.properties` with PostgreSQL connection details:
    ```properties
    spring.application.name=managify
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    spring.datasource.driver-class-name=
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    ```
  - Created a local "Managify" database.
- **Security Setup**: Implemented a custom security configuration and added JSON Web Token (JWT) authentication.

### 21 October  
- **User Model Creation**:
   - Defined `User` entity with JPA, including fields `id`, `fullName`, `email`, `password`, and `projectSize`.
   - Established a one-to-many relationship with `Issues` entity.
- **Authentication Setup**:
   - Developed `AuthController` for signup and signin with JWT token generation.
   - Incorporated `UserRepository` and `PasswordEncoder` for database operations and secure password handling.
- **JWT Generation & Fixes**:
   - Implemented JWT generation but resolved a `WeakKeyException` by switching to a 256-bit secure key.

### 22 October  
- **Database Entity Corrections**:
   - Added `@Table(name = "users")` to `User` entity to resolve table naming conflicts.
- **Password Matching Issue Fix**:
   - Corrected `PasswordEncoder` usage to ensure proper credential validation during sign-in.
- **JWT Finalization**:
   - Integrated JWT-based authentication successfully for both signup and signin.

### 23 October  
#### Database Schema Overview  
- **Entities**:
   - **Users**: Manages user details like name, email, and project count.
   - **Issues**: Tracks project-related issues with fields like title, status, priority, and tags.
   - **Projects**: Contains project data, including name, description, and category.
   - **Chat**: Represents user-project group chats.
   - **Messages**: Stores chat messages with timestamps.
   - **Comments**: Tracks comments on issues.
- **Relationships**:
   - Users relate to Issues and Comments.
   - Issues connect to Projects and Comments.
   - Chat links Messages, Users, and Projects.

### 24 October  
- **Schema and Workflow Documentation**: Drafted database schema and visualized main entity relationships.

### 25 October  
- **Service Enhancements**:
   - Improved `ChatService`, `ProjectService`, and `UserService` interfaces and implementations to enhance functionality and performance.
- **Repository Optimization**:
   - Adjusted repositories for improved data retrieval and storage.

### 26 October  
- **DTO Enhancements**:
   - Refined `IssueDTO.java` for better data handling and issue management.
- **Controller Updates**:
   - Enhanced multiple controllers (`AuthController`, `CommentController`, `IssueController`, etc.) to improve functionality and add new endpoints.
- **Service Interface Extensions**:
   - Extended interfaces like `CommentService`, `EmailService`, and `IssueService` to support expanded functionalities.
- **Model Adjustments**:
   - Refined `Message.java`, `PlanType.java`, and `Subscription.java` for better compatibility and functionality.
- **Repository Layer Enhancements**:
   - Improved repositories (e.g., `CommentRepository`, `IssueRepository`) to support more complex queries.
- **Request & Response Updates**:
   - Refined request and response objects for consistency and improved API response structure.
- **Service Implementations**:
   - Updated various service implementations (e.g., `CommentServiceImp`, `EmailServiceImp`) to enhance business logic and performance.
