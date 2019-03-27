# rss-feed-server

Student project

## Get started

This API can be started with Maven or using IntelliJ.

##### Some notes

Routes notified with `[Secured]` require a valid JWT token inside the header request.

Here an example: 
```
curl -X GET\
 http://localhost:8080/users/me\
 -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU1MzcxOTU4NiwiZXhwIjoxNTUzNzIzMTg2fQ.cqQsZ30B_WeV-Ia4PiCCzPTvhZBu4eklAGhjvWZsmbQ"
```

#### ROUTES `/feeds`
 - GET /feeds => Return all feeds already registered
 - GET /feeds/{id} => Return feed with selected {id}
 - POST /feeds => Save feedCandidate found in request body
 - DELETE /feeds/{id} => Remove feed with selected {id}

>  NO Update available -> By default, rss are regularly updated

#### ROUTES `/auth`
 - POST `/signin` => Connect to an account

Wait for :
 ```json
 {
    "username": "test",
    "password": "some-heavy-password"
 }
 ```
Return (ex): 
 ```json
  {
    "username": "test",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU1MzcxOTU4NiwiZXhwIjoxNTUzNzIzMTg2fQ.cqQsZ30B_WeV-Ia4PiCCzPTvhZBu4eklAGhjvWZsmbQ"
  }
``` 
 
#### ROUTES `/users`
 - GET `/me` `[Secured]` => Return information of current user
 
Return (ex):
```json
  {
    "roles": ["ROLE_USER"],
    "username": "user"
  } 
```
    