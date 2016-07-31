# Mobile Screener API

## Dependencies

MongoDB is needed.

## Packaging

To package application run:

```
mvn clean package
```

## Running

To start application run:

```
java -jar target/mobile-screener-api-1.0.0-SNAPSHOT.jar server configuration.yml
```

Application will be started with default configuration. You can can set following ENV properties to change behavior:

* PORT - default is 8080
* LOG_LEVEL - default is DEBUG
* MONGO_HOST - default is localhost
* MONGO_PORT - default is 27017
* DB_SEED - whether to import seed.json to MongoDB

See [configuration.yml](configuration.yml) for more info about configuration.

### Run as Docker Images

```
# build image, application must be packaged before
docker build -t vchalupa/mobile-screener .

# run docker compose
docker-compose up
```

The API is then on port `8080`.

In case you do not want to seed the DB, change the `DB_SEED` in `docker-compose.yml` to `no`.

## Tests

To execute tests run:

```
mvn clean test
```

## Resource

### GET /api/users

Returns all users.

#### Query Parameters

* `filter`: filtering, expected format is `field comparator,value`:
  * `username ==,john`
  * `location.street ==, xyz`
* `orderBy`: sorting, allowed multiple comma separated values:
  * `orderBy=username,-email`
* `limit`: limit of returned items - pagination, default is 5, max is 10
* `offset`: offset of returned items - pagination, default is 0

#### Response Headers

* `X-TotalCount`: total number of records, needed for client pagination


### GET /api/user/{id}

Gets concrete user.

#### CREATE /api/user

Creates new user.

#### Response Headers

* `Location`: location of newly created user

#### PUT /api/user/{id}

Updates concrete user.

### DELETE /api/user/{id}

Removes concrete notifications.

## TODO

* Follow `// TODO:` for notes.
* Improve query parameter validation.
* Improve entity validation rules.
* Use FIQL for filtering.
