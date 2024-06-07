# ReelRadar

ReelRadar is a single page web app that helps you explore movies, TV shows, and celebrities. You can also register and log in for a personalized experience.  
Check out ReelRadar on the [demo Website](https://miguelcardoso19.github.io/ReelRadar/#/).

![ReelRadar Landing Page](https://github.com/MiguelCardoso19/ReelRadar/blob/demo/landingpage.jpg)

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Next Implementations](#next-implementations)
- [ReelRadar Backend/API Endpoints](#reelradar-backendapi-endpoints)
  - [AuthController](#authcontroller)
  - [FavoriteController](#favoritecontroller)
  - [UserController](#usercontroller)

## Features

- See lists of movies, TV shows, and celebrities from the MovieDB API.
- Click on an item to view its details.
- Use the search bar and buttons for easy navigation.
- Register and login to save favorites.

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
- Maven

## Next Implementations

- Implement saving favorites for each user functionality. (Ongoing)
- Improve responsiveness for better user experience on various devices.
- Add the ability to search items by genres.

## ReelRadar Backend/API Endpoints

ReelRadar's backend, based on Spring Boot, uses Spring Security and JSON Web Tokens (JWT) for authentication. All requests need authentication, guaranteeing only authorized users access API endpoints. JWT tokens validate authenticity. CORS configurations boost security by permitting specific origins to access server resources.

### AuthController

- **Login**: `POST /api/auth/login`
  - **Description**: Logs a user in.
  - **Request Body**:
    ```json
    {
      "username": "user",
      "password": "password"
    }
    ```
  - **Response**:
    - **Success**: Returns a JWT token, username and userID.
    - **Failure**: Returns an error.

- **Register**: `POST /api/auth/register`
  - **Description**: Registers a new user.
  - **Request Body**:
    ```json
    {
      "username": "user",
      "password": "password"
    }
    ```
  - **Response**:
    - **Success**: Returns a JWT token, username and userID.
    - **Failure**: Returns an error.
 
### UserController

- **Delete User**: `DELETE /api/user/delete`
  - **Description**: Deletes the user account.
  - **Request Body**:
    ```json
    {
      "username": "user"
    }
    ```
  - **Response**: Returns a success message.

### FavoriteController

- **Get Favorites**: `GET /api/favorites/show/{userId}`
  - **Description**: Retrieves favorites for a user.
  - **Path Parameter**:
    - `userId`: The ID of the user.
  - **Response**:
    - **Success**: Returns the user's favorite items.
    - **Failure**: Returns an error if no favorites are found.

- **Add Favorite**: `POST /api/favorites/add`
  - **Description**: Adds a new favorite item.
  - **Request Body**: Favorite item details.
  - **Response**: Returns the created favorite item.

- **Remove Favorite**: `DELETE /api/favorites/remove/{favoriteId}`
  - **Description**: Removes a favorite item.
  - **Path Parameter**:
    - `favoriteId`: The ID of the favorite item.
  - **Response**: Returns a success message.
