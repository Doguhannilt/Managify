
## Project is done
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

### 27 October   
- Updated JWT configuration files (`JwtConstant`, `JwtProvider`, and `JwtTokenValidator`) to enhance token validation and security mechanisms.
- Improved error handling in `IssueController` for better exception management.

### 28 October 
- Enhanced `PaymentController`, `ProjectController`, and `UserController` to improve error handling and user validation.
- Minor updates to the `User` model, adding validation layers to strengthen data integrity and security.

### 29 October   
- Updated `CommentRepository` and `ProjectRepository` with optimizations for query performance.

### 30 October   
- Improved functionality and structure in `CommentServiceImp`, `MessageServicesImp`, and `ProjectServiceImp` to streamline service logic and improve code readability. 


## POSTMAN: <a href="https://www.postman.com/spacecraft-astronaut-67997412/managify/overview">Click me!</a>
| **Endpoint**                            | **Method** | **Description**                      |
|-----------------------------------------|------------|--------------------------------------|
| `/auth/signup`                          | POST       | Register a new user                 |
| `/auth/signing`                         | POST       | Log in a user                       |
| `/api/users/profile`                    | GET        | Retrieve user profile               |
| `/api/projects`                         | POST       | Create a new project                |
| `/api/projects/{id}`                    | GET        | Get project by ID                   |
| `/api/projects/{id}`                    | DELETE     | Delete project by ID                |
| `/api/projects`                         | GET        | Get all projects                    |
| `/api/projects/search?keyword={keyword}`| GET        | Search projects by keyword          |
| `/api/issues`                           | POST       | Create a new issue                  |
| `/api/issues/{id}`                      | GET        | Get issue by user ID                |
| `/api/issues/project/{projectId}`       | GET        | Get all issues by project ID        |
| `/api/issues/{id}/status/{status}`      | PUT        | Update issue status                 |
| `/api/issues/{id}/status/{status}`      | DELETE     | Delete issue status                 |
| `/api/issues/{id}/assignee/{userId}`    | PUT        | Assign an issue to a user           |
| `/api/projects/{id}/chat`               | GET        | Retrieve chat by project ID         |
| `/api/messages/chat/{projectId}`        | GET        | Get messages by project ID          |
| `/api/messages/send`                    | POST       | Send a new message                  |
| `/api/comments/{id}`                    | GET        | Get a comment by ID                 |


### 3 November

- **Cache management** has been implemented in the project controller.
- The following endpoints now utilize caching:
  - **GET /api/projects**: The endpoint for listing projects has been cached based on user ID, along with category and tag filters.
  - **GET /api/projects/{projectId}**: The endpoint for retrieving details of a single project has been cached using the project ID.
  - **PATCH /api/projects/{projectId}**: When updating a project, the associated cache entry is updated accordingly.
  - **DELETE /api/projects/{projectId}**: Upon deleting a project, the corresponding project data is removed from the cache.
  - **GET /api/projects/search**: The endpoint for searching projects by keyword now caches the results.


### 4 November

- **Test for Saving User Information**: Created tests to ensure that user information can be saved successfully in the database and verified by retrieving the saved user by email.

- **Find By Email Test**: Implemented a test to check if a user can be found by their email address and validated the retrieved user's details.

- **Find All Users Test**: Added a test to confirm that all users can be retrieved from the database and ensured the list is not empty.

- **Find Owner's Projects Test**: Developed a test to find all projects owned by a specific user and validated that the owner ID matches the expected user's ID.

- **Count Projects By User Test**: Implemented a test to verify the number of projects associated with a user.

- **Find Project By ID Test**: Created a test to ensure that a project can be found using its ID and validated that the correct project is returned.

- **Delete Project Test**: Added a test to confirm that a project can be deleted from the database and that it no longer exists afterward.

- **Validate Project Owner and Name Test**: Implemented a test to check that each project returned for a user has the correct owner ID and a non-null project name.



### 5 November

- **Comment Service**:
  - **createComment**: Verified successful creation with valid `issueId` and `userId`; handled exceptions for invalid IDs.
  - **deleteComment**: Confirmed deletion functionality, validated exception handling for non-existent comments, invalid users, and unauthorized deletions.
  - **findCommentByIssue**: Checked that the method returns a non-empty list of comments for a given `issueId`.

- **Issue Service**:
  - **getIssueById**: Ensured retrieval of issues by ID and proper exception handling for non-existent issues.
  - **getIssueByProjectId**: Validated that issues can be retrieved by `projectId` and checked for exceptions if the project is not found.
  - **createIssue**: Verified successful issue creation with valid data and project association.
  - **deleteIssue**: Confirmed deletion of issues with appropriate checks for existence.
  - **addUserToIssue**: Ensured users are added to issues and validated that proper exceptions are thrown for non-existent users and issues.
  - **updateStatus**: Verified that issue status updates function correctly with valid IDs.

- **User Service**:
  - **loadUserByUsername**: Mocked user retrieval by email and ensured proper exception handling for non-existent users.

- **Project Service**:
  - **getProjectById**: Validated project retrieval by ID with appropriate exception handling for non-existent projects.
