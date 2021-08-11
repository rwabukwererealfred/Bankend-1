# Bankend-1

## Technology used
-	Java: Spring boot framework
-	Database: mysql


## End points

### end point without JWT token authentication

| Methods       | Endpoints            | Actions            | Parameter  |
| ------------- |:-------------------  | ------------------:|:----------:|
| POST          | /api/createToken     | Create token       |username    |

### End point be accessible via JWT token based authentication

| Methods       | Endpoints         | Actions                                     | Parameter |
| ------------- |:---------------   |:------------------------------------------  |:---------:|
| POST          |/api/uploadFile    | import file into user list                  | file      | 
| GET           |/api/users         | return users who are having invalid input   |           |
| POSST         |/api/saveUser      | commit the list uploaded in a sql database  |           |
