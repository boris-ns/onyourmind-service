# Auth
Work in progress

# Users
Work in progress

# Posts
Work in progress

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