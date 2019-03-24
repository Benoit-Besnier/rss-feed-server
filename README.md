# rss-feed-server

Student project

## Get started

This is a REST API which fetch and save RSS-feeds on demand.

Here a collection of all routes and HTTP verbs supported.

#### ROUTES `/feeds`
 - GET /feeds => Return all feeds already registered
 - GET /feeds/{id} => Return feed with selected {id}
 - POST /feeds => Save url found in request body
 - DELETE /feeds/{id} => Remove feed with selected {id}

>  NO Update available -> By default, rss are regularly updated   