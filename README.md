# ReelRadar

ReelRadar is a single-page web app designed to help you explore movies, TV shows, and celebrities. Register and log in for a personalized experience, including the ability to add items to your favorites.  
Check out ReelRadar on the [demo Website](https://miguelcardoso19.github.io/ReelRadar/#/).

![ReelRadar Landing Page](https://github.com/MiguelCardoso19/ReelRadar/blob/demo/landingpage.jpg)

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Next Implementations](#next-implementations)
- [ReelRadar Backend/API Endpoints](#reelradar-backendapi-endpoints)
  - [Authentication](#authenticationcontroller)
  - [Favorite](#favoritecontroller)
  - [User](#usercontroller)

## Features

- View lists of movies, TV shows, and celebrities from the MovieDB API
- Click on items to see detailed information
- Use the search bar and navigation buttons for easy access
- Register and log in to save and manage your favorites

## Technologies Used

- JavaScript 
- HTML 
- CSS 
- JQuery 
- BootStrap 
- Java 21
- PostgreSQL
- Spring Boot 3
- Spring Security
- Spring Data JPA 
- Lombok
- JSON Web Token (JWT) 
- Mockito
- JUnit 5
- Maven
- OpenAPI/Swagger
- Docker

## Next Implementations

**Backend:**
- Mail Service: Implement email confirmation for account creation and deletion (Ongoing).
  
**Frontend:**
- Responsiveness: Enhance responsiveness for a better user experience across devices.
- Search Functionality: Add a search feature to filter items by genres.

## ReelRadar Backend/API Endpoints

ReelRadar's backend, built with Spring Boot, incorporates several key technologies and practices to ensure secure and efficient operations:

- Spring Security: Handles authentication and authorization, ensuring that all API requests are protected. Only authorized users can access the API endpoints.
- JSON Web Tokens (JWT): Used for authenticating requests and validating the authenticity of the tokens.
- CORS (Cross-Origin Resource Sharing): Configured to allow only specific origins to access server resources, enhancing security.  

All controllers are documented using OpenAPI/Swagger, providing interactive API documentation and testing tools.

### AuthenticationController

- **Login: POST /api/auth/login**
  - **Description**: Authenticates a user with their username and password. If successful, returns a JWT token for accessing protected resources.
  - **Request Body**:
    ```json
    {
      "username": "user",
      "password": "password"
    }
    ```
  - **Response**:
    - **Success**: Returns a JSON object with the username, userID, and JWT token.
    - **Failure**: Returns an error message if authentication fails.

- **Register: POST /api/auth/register**
  - **Description**: Registers a new user with the provided details. If registration is successful, a JWT token is returned for accessing protected resources.
  - **Request Body**:
    ```json
    {
      "username": "user",
      "password": "password"
    }
    ```
  - **Response**:
    - **Success**: Returns a JSON object with the username, userID, and JWT token.
    - **Failure**: Returns an error message if registration fails.

- **Logout: POST /api/auth/logout**
  - **Description**: Logs out the current user by invalidating their JWT token. The token must be provided in the request header.
  - **Request Header**:
    - `Authorization`: Bearer token (JWT)
  - **Response**:
    - **Success**: Returns a success message indicating that the user has been logged out.
    - **Failure**: Returns an error message if the token is invalid or not provided.

### UserController

- **Delete User: DELETE /api/user/delete**
  - **Description**: Deletes a user based on the provided details in the request body.
  - **Request Body**:
    ```json
    {
      "username": "user"
    }
    ```
  - **Response**:
    - **Success**: Returns a success message if the user is deleted successfully.
    - **Failure**: Returns an error message if the user is not found.

- **Update User: PUT /api/user/update/{username}**
  - **Description**: Updates user details based on the provided username and request body.
  - **Path Parameter**:
    - `username`: The username of the user to update.
  - **Request Body**:
    ```json
    {
      "username": "newUsername",
      "email": "newEmail@example.com",
      "password": "newPassword"
    }
    ```
  - **Response**:
    - **Success**: Returns a success message if the user is updated successfully.
    - **Failure**: Returns an error message if the user is not found.

- **Get User Details: GET /api/user/details/{username}**
  - **Description**: Retrieves user details based on the provided username.
  - **Path Parameter**:
    - `username`: The username of the user whose details are to be retrieved.
  - **Response**:
    - **Success**: Returns user details including username and email.
    - **Failure**: Returns an error message if the user is not found.

### FavoriteController

- **Get Favorites: GET /api/favorites/show/{userId}**
  - **Description**: Retrieves a list of movies, TV shows, or people favorited by the specified user.
  - **Path Parameter**:
    - `userId`: The ID of the user.
  - **Response**:
    - **Success**: Returns a list of the user's favorite items.
    - **Failure**: Returns an error message if no favorites are found.

- **Add Favorite: POST /api/favorites/add/{userId}**
  - **Description**: Adds a new favorite item (movie, TV show, or person) for the user.
  - **Path Parameter**:
    - `userId`: The ID of the user.
  - **Request Body**:
    ```json
    {
      "movies": ["movie1", "movie2"],
      "people": ["person1"],
      "tvShows": ["tvShow1"]
    }
    ```
  - **Response**:
    - **Success**: Returns a success message indicating the favorite item was added successfully.
    - **Failure**: Returns an error message if the favorite item could not be added.

- **Remove Favorite: DELETE /api/favorites/remove/{userId}**
  - **Description**: Removes a specific favorite item (movie, TV show, or person) for the user.
  - **Path Parameter**:
    - `userId`: The ID of the user.
  - **Request Body**:
    ```json
    {
      "type": "movies",
      "value": "movieToRemove"
    }
    ```
  - **Response**:
    - **Success**: Returns a success message indicating the favorite item was removed successfully.
    - **Failure**: Returns an error message if the favorite item could not be removed.
