# Auth
## Login
```POST http://localhost:8080/auth/login```  
Request:  
```json
{
	"username":"john.doe",
	"password":"123"
}
```
Response:  
```json
{
    "id": 1,
    "username": "john.doe",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@doe.com",
    "enabled": true,
    "authorities": [
        "ROLE_USER"
    ],
    "token": {
        "accessToken": "JWT_TOKEN_VALUE",
        "expiresIn": 3600000
    }
}
```

## Refresh token
```POST http://localhost:8080/auth/refresh```  
Authentication: you must send JWT with request  
Response:  
```json
{
    "accessToken": "JWT_TOKEN_VALUE",
    "expiresIn": 3600000
}
```

## Change password
```POST http://localhost:8080/auth/change-password```  
Authentication: you must send JWT with request  
Request:  
```json
{
    "oldPassword": "12345",
    "newPassword": "asdfg"
}
```
Response: 200 OK or exception

# Users
## Get 1 user
```GET http://localhost:8080/api/users/public/{USER_ID}```  
Response:  
```json
{
    "id": 1,
    "username": "jane.doe",
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane@doe.com",
    "enabled": true,
    "authorities": [
        "ROLE_USER"
    ],
    "token": null
}
```

## Get all users
```GET http://localhost:8080/api/users```  
Authorization: *ROLE_ADMIN*  
Response: 
```json
[
    {
        "id": 1,
        "username": "jane.doe",
        "firstName": "Jane",
        "lastName": "Doe",
        "email": "jane@doe.com",
        "enabled": true,
        "authorities": [
            "ROLE_USER"
        ],
        "token": null
    },
    /* ... */
]
```

## Register new user
```POST http://localhost:8080/api/users/public/add-user```  
Request:  
```json
{
	"username": "jane.doe",
	"password": "123",
	"firstName": "Jane",
	"lastName": "Doe",
	"email": "jane@doe.com"
}  
```  
Response:  
```json
{
    "id": 1,
    "username": "jane.doe",
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane@doe.com",
    "enabled": true,
    "authorities": [
        "ROLE_USER"
    ],
    "token": null
}
```

## Register new admin
```POST http://localhost:8080/api/users/add-admin```  
Authorization: *ROLE_ADMIN*  
Request:  
```json
{
	"username": "jane.doe",
	"password": "123",
	"firstName": "Jane",
	"lastName": "Doe",
	"email": "jane@doe.com"
}  
```  
Response:  
```json
{
    "id": 1,
    "username": "jane.doe",
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane@doe.com",
    "enabled": true,
    "authorities": [
        "ROLE_ADMIN"
    ],
    "token": null
}
```

## Deactivate user
```PUT http://localhost:8080/api/users/deactivate/{USER_ID}```  
Authorization: *ROLE_ADMIN*  
Response: 200 OK or exception  
Changes 'enabled' flag to false

## Activate user
```PUT http://localhost:8080/api/users/activate/{USER_ID}```  
Authorization: *ROLE_ADMIN*  
Response: 200 OK or exception  
Changes 'enabled' flag to true

# Posts
## Get 1 post
```GET http://localhost:8080/api/posts/public/{POST_ID}```  
Response:  
```json
{
    "id": 1,
    "text": "This is just some random text for testing purposes",
    "dateTime": "2019-09-16T14:42:29.074+0000",
    "likes": 0,
    "dislikes": 0,
    "enabled": true,
    "authorId": 1,
    "authorName": "John Doe",
    "comments": [
        {
            "id": 1,
            "text": "This is text for one comment",
            "dateTime": "2019-09-16T14:42:30.689+0000",
            "authorName": "John Doe",
            "authorId": 1,
            "postId": 1,
            "enabled": true
        }
    ]
}
```

## Get all posts
```GET http://localhost:8080/api/posts/public```  
Response: List of all post objects, see example response above

## Get all posts from user
```GET http://localhost:8080/api/posts/public/user/{USER_ID}```  
Response: list of all post objects, see example response above

## Add post
```POST http://localhost:8080/api/posts```  
Authorization: *ROLE_USER*  
Request:  
```json
{
	"text":"This is just some random text for testing purposes"
}
```
Response:  
```json
{
    "id": 1,
    "text": "This is just some random text for testing purposes",
    "dateTime": "2019-09-16T14:42:29.074+0000",
    "likes": 0,
    "dislikes": 0,
    "enabled": true,
    "authorId": 1,
    "authorName": "John Doe",
    "comments": []
}
```  

## Edit post
```PUT http://localhost:8080/api/posts```  
Authorization: *ROLE_USER*, same one that created post  
Request:  
```json
{
	"id":1,
	"text": "This is new text"
}
```
Response:  
```json
{
    "id": 1,
    "text": "This is new text",
    "dateTime": "2019-09-16T14:42:29.074+0000",
    "likes": 0,
    "dislikes": 0,
    "enabled": true,
    "authorId": 1,
    "authorName": "John Doe",
    "comments": []
}
```

## Delete post
```DELETE http://localhost:8080/api/posts/{POST_ID}```  
Authorization: *ROLE_USER*, same one that created the post  
Response: 200 OK or exception

## Deactivate post
```PUT http://localhost:8080/api/posts/deactivate/{POST_ID}```  
Authorization: *ROLE_ADMIN*  
Response: 200 OK or exception  
Changes 'enabled' flag to false

## Activate post
```PUT http://localhost:8080/api/posts/activate/{POST_ID}```  
Authorization: *ROLE_ADMIN*  
Response: 200 OK or exception  
Changes 'enabled' flag to true


# Comments
## Get 1 comment
```GET http://localhost:8080/api/comments/public/{COMMENT_ID}```  
Response:  
```json
{
    "id": 1,
    "text": "This is text for one comment",
    "dateTime": "2019-09-16T14:42:30.689+0000",
    "authorName": "John Doe",
    "authorId": 1,
    "postId": 1,
    "enabled": true
}
```

## Get all comments
```GET http://localhost:8080/api/comments/public```  
Response: List of Comment objects (see above)

## Get all comments from user
```GET http://localhost:8080/api/comments/public/user/{USER_ID}```  
Response: List of Comment objects (see above)  

## Add comment
```POST http://localhost:8080/api/comments```  
Authorization: *ROLE_USER*  
Request:  
```json
{
    "text": "This is a comment",
    "postId": 1
}
```
Response:  
```json
{
    "id": 1,
    "text": "This is a comment",
    "dateTime": "2019-09-16T14:42:30.689+0000",
    "authorName": "John Doe",
    "authorId": 1,
    "postId": 1,
    "enabled": true
}
```

## Delete comment
```DELETE http://localhost:8080/api/comments/{COMMENT_ID}```  
Authorization: *ROLE_USER*, same one that created the comment  
Response: 200 OK or exception

## Edit comment
```PUT http://localhost:8080/api/comments```  
Authorization: *ROLE_USER*, same one that created the comment  
Request:  
```json
{
    "id": 1,
    "text": "new comment text"
}
```
Response:  
```json
{
    "id": 1,
    "text": "new comment text",
    "dateTime": "2019-09-16T14:42:30.689+0000",
    "authorName": "John Doe",
    "authorId": 1,
    "postId": 1,
    "enabled": true
}
```

## Deactivate comment
```PUT http://localhost:8080/api/comments/deactivate/{COMMENT_ID}```  
Authorization: *ROLE_ADMIN*  
Response: 200 OK or exception  
Changes 'enabled' flag to false

## Activate comment
```PUT http://localhost:8080/api/comments/activate/{COMMENT_ID}```  
Authorization: *ROLE_ADMIN*  
Response: 200 OK or exception  
Changes 'enabled' flag to true