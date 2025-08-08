# GitHub Repository Lister API

A simple Spring Boot REST API that lists all non-fork GitHub repositories for a given user, including each branch’s name and last commit SHA.

If the user does not exist, the API returns a 404 response with an error message.

---

## API Endpoint

GET /users/{username}/repos


- **Path parameter:**  
  `username` : GitHub username

- **Response (200 OK):**  
  JSON array of repositories with their branches, for example:

```json
[
  {
    "repositoryName": "example-repo",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "a1b2c3d4"
      }
    ]
  }
]
```

- **Response (404 Not Found):**  
```json
[
  {
  "status": 404,
  "message": "GitHub user not found"
}
]
```
---

## How to Run

### Prerequisites

- Java 17 or higher  
- Maven 3.x 

### Build and Run

```bash
mvn clean install
mvn spring-boot:run
```
The application will start on http://localhost:8080
