# Expense Tracker

## How to run
Run ExpensetrackerApplication.java and see the output in console


## Curl
### Create a new User

```
curl --location --request POST 'localhost:8080/api/v1/user/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Joe Goldberg",
    "email": "joeg@gmail.com",
    "mobile": "9999988881"
}'
```

### Get Users
```
curl --location --request GET 'localhost:8080/api/v1/user/'
```

### Get User by Id
```
curl --location --request GET 'localhost:8080/api/v1/user/0'
```

### Create a new Group
```
curl --location --request POST 'localhost:8080/api/v1/group/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Household shopping",
    "description": "Household shopping expenses",
    "createdBy": 0
}'
```

### Get Groups
```
curl --location --request GET 'localhost:8080/api/v1/group/'
```

### Get Group by Id
```
curl --location --request GET 'localhost:8080/api/v1/group/0'
```

### Add a User to a Group
```
curl --location --request POST 'localhost:8080/api/v1/group/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "groupId": 0,
    "userMobile": "9999988882",
    "authority": "USER"
}'
```

### Remove a User from a Group
```
curl --location --request DELETE 'localhost:8080/api/v1/group/0/user/0'
```
