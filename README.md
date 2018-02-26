# BookCatalog

BookCatalog is an application with REST API for book cataloguing.

## Features
- Account registration
- Login via basic authentication
- Creating shelves (book lists)
- Adding and removing books from shelves
- Modifying book information
- Modifying shelf names

## Technologies used
- Java 8
- Spring (Spring Boot, Spring MVC, Spring Security, Spring Data)
- Hibernate
- JUnit, Mockito

## Endpoints
- No authentication required:  

  - **POST** `/registration` - creates a new user account
    
    ```javascript
    {
        "username": "example",
        "email": "e@mail.com",
        "password": "password",
        "confirmPassword": "password"
    }
    ```
    It's required to confirm the account through link sent to your email, so you might want to use a test account:
    **`test1@test.com`** / **`test1`**
	
	For registration to work, two environment variables have to be set:
    ```
    mail.username=	(gmail domain)
    mail.password=
    ```
    The email with activation link with be sent from this account.
	
  **Only basic authentication is supported.**

- Authentication required:  

  - **POST** `/shelf` - creates a new shelf
    ```javascript
    {
       "name": "reading"
    }    
    ```
  - **POST** `/book` - creates a book and adds it to shelves with id specified in a `shelfIds` field
    ```javascript
    {
        "title":  "Harry Potter and the Sorcerer's Stone",
        "authors": [
            {
                "name": "J. K. Rowling"
            }
        ],
        "description": "Nice book",
        "pages": 250,
        "publishedDate": "1997/06/26",
        "publisher": "Scholastic",
        "genres": [
            {
                "name": "Fantasy"
            }
        ],
        "shelfIds": [3]
    }
    ```
  - **POST** `/shelf/{shelfId}/book` - creates a books and adds it to shelf with id `shelfId`  
	```
	JSON object as above, without the `shelfIds` field
    ```
  - **GET** `/books` - return all user's books
		
  - **GET** `/book/{id}` - returns book with specified id

  - **GET** `/shelves` - returns all user's shelves

  - **GET** `/shelf/{id}/books` - return user's books on a shelf with specified id 

  - **DELETE** `/book/{id}` - remove a book with specified id from all user's shelves

  - **DELETE** `/shelf/{shelfId}/book/{bookId}` - removes a book with id `bookId` from shelf with id `shelfId`

  - **PUT** `/book/{id}` - modifies book information by replacing it with the specified data. Not present fields will be set to null.
    ```javascript
    {
        "title":  "Harry Potter and the Sorcerer's Stone",
        "description": "Nice book"
    }
    ```
  - **PATCH** `/book/{id}` - modifies book information by changing only the specified fields
    ```javascript
    {
        "title":  "Harry Potter and the Sorcerer's Stone",
        "pages": 300
    }
    ```
  - **PATCH** `/shelf/{id}` - changes shelf name
    ```javascript
    {
        "name": "new name"
    }
    ```

