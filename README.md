# rss-feed-server

Student project

## Get started

This API can be started with Maven or using IntelliJ.

##### Some notes

Routes marked with **[Secured]** require a valid JWT token inside the header request.

Here an example: 
```text
curl -X GET\
 http://localhost:8080/users/me\
 -H "Authorization: Bearer <some extra long JWT token>
```

#### ROUTES `/feeds`
    
> This route give access to **feed** resource.  
> For now, **feed** resource **come with all** theirs **items** (which cannot be fetch alone)
    
##### GET `/feeds` 
 
 Give access to the list of all feed currently registered.

###### Expect
```text
    None
```
###### Result
```json
[
   {
       "uuid": "cd3db99c-5d56-458c-a124-65bd6e6c9389",
       "title": "Le Monde.fr - Actualités et Infos en France et dans le monde",
       "link": "https://www.lemonde.fr/rss/une.xml",
       "sourceFeedUrl": "https://www.lemonde.fr/rss/une.xml",
       "copyright": "Le Monde - L'utilisation des flux RSS du Monde.fr est réservée à un usage strictement personnel, non professionnel et non collectif. Toute autre exploitation doit faire l'objet d'une autorisation et donner lieu au versement d'une rémunération. Contact : droitsdauteur@lemonde.fr",
       "description": "Le Monde.fr - 1er site d'information. Les articles du journal et toute l'actualité en continu : International, France, Société, Economie, Culture, Environnement, Blogs ...",
       "authors": [],
       "categories": [],
       "contributors": [],
       "encoding": null,
       "entries": [
           {
               "id": null,
               "feed": null,
               "link": "https://www.lemonde.fr/politique/article/2019/04/08/le-bilan-du-grand-debat-en-six-questions_5447417_823448.html?xtor=RSS-3208",
               "authors": [],
               "categories": [],
               "comments": null,
               "contributors": [],
               "links": [],
               "publishedDate": "2019-04-08T10:40:27.000+0000",
               "title": "Le bilan du grand débat en six questions",
               "updatedDate": null,
               "uri": "https://www.lemonde.fr/tiny/5447417/"
           },
          {...}
       ],
       "feedType": "rss_2.0",
       "generator": null,
       "language": null,
       "links": [],
       "publishedDate": "2019-04-08T17:03:12.000+0000",
       "styleSheet": null,
       "uri": null,
       "webMaster": null,
       "autoUpdatedDate": null
   },
   {...}
]
```

##### GET `/feeds/{id}` 
 
 Give access to one feed instance currently registered.

###### Expect
```text
    None
```
###### Result
```json
{
   "uuid": "cd3db99c-5d56-458c-a124-65bd6e6c9389",
   "title": "Le Monde.fr - Actualités et Infos en France et dans le monde",
   "link": "https://www.lemonde.fr/rss/une.xml",
   "sourceFeedUrl": "https://www.lemonde.fr/rss/une.xml",
   ...
   "entries": [
       {
           "link": "https://www.lemonde.fr/politique/article/2019/04/08/le-bilan-du-grand-debat-en-six-questions_5447417_823448.html?xtor=RSS-3208",
           ...
           "publishedDate": "2019-04-08T10:40:27.000+0000",
           "title": "Le bilan du grand débat en six questions",
           "uri": "https://www.lemonde.fr/tiny/5447417/"
       },
      {...}
   ],
  ...
}
```

##### POST `/feeds` **[Secured]**
 
 Register a new RSS feed. Can only be done by **connected users**.

###### Expect
```json
{
    "url": "https://www.lemonde.fr/rss/une.xml"
}
```
###### Result
```text
    code : 201 Create
```


##### DELETE `/feeds/{id}` **[Secured ADMIN]**
 
 Delete one RSS feed already registered . Can only be done by **connected administrator**.

###### Expect
```text
    None
```
###### Result
```text
    code : 204 No Content
```


>  NO Update available -> By default, rss are regularly updated   

#### ROUTES `/auth`
    
> This route allow registration of a new user and the connection with one valid account.  

##### POST `/auth/register`
 
 Create a new user.

###### Expect
```json
{
    "username": "test",
    "password": "some-really-heavy_password"
}
```
###### Result
```text
    code: 201 Created
```

##### POST `/auth/signin`
 
 Connect a previously registered user.

###### Expect
```json
{
	"username": "user",
	"password": "some-really-heavy_password"
}
```
###### Result
```json
{
    "username": "user",
    "token": "<some extra long JWT token>"
}
```

 
 #### ROUTES `/users`
     
 > This route give access to **user** resource.  
 
 ##### POST `/users/me` **[Secured]**
  
  Allow connected users to get its own profile.
 
 ###### Expect
 ```text
    None
 ```
 ###### Result
 ```json
 {
     "roles": [
         "ROLE_USER"
     ],
     "preferredFeeds": [
         "cd3db99c-5d56-458c-a124-65bd6e6c9389"
     ],
     "username": "user"
 }
 ```

 ##### PUT `/users/me/feeds` **[Secured]**
  
  Allow user to update is preferred feed list.
 
 ###### Expect
 ```json
{
    "preferredFeeds": [
        "cd3db99c-5d56-458c-a124-65bd6e6c9389"
    ]
}
 ```
 ###### Result
```text
    code : 204 No Content
```
 