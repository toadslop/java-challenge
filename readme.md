# Sample Employee Api

## Setup

To run this application, you'll need to create a .env folder in the application root.
Inside the file, provide the variables ADMIN_USER (the username of the admin user)
and ADMIN_PASS (the password of the admin user.) Once this is done, you'll be able
to make authenticated requests with a Basic Authorization header.

For example, the file might look like this:

```txt
ADMIN_USER=admin
ADMIN_PASS=password
```

To format the header properly, base64 encode your username and password in this format: `username:password`.

After that, construct the header as follows: `Authorization: Basic YWRtaW46cGFzc3dvcmQ=`.

After that, you can run the following commands to create, read, update, and delete users.

```bash
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api/v1/employees' -H "Authorization: Basic YWRtaW46cGFzc3dvcmQ="

curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' 'http://localhost:8080/api/v1/employees?id=0&name=Sally&salary=200000&department=Finance' --header "Authorization: Basic YWRtaW46cGFzc3dvcmQ="

curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api/v1/employees/1' -H "Authorization: Basic YWRtaW46cGFzc3dvcmQ="

curl -X PUT --header 'Content-Type: application/json' --header 'Accept: */*' -d '{"name": "Billy" }' 'http://localhost:8080/api/v1/employees/1' --header "Authorization: Basic YWRtaW46cGFzc3dvcmQ="

curl -X DELETE --header 'Accept: */*' 'http://localhost:8080/api/v1/employees/1' --header "Authorization: Basic YWRtaW46cGFzc3dvcmQ="
```

### If I Had More Time

If I had more time I would...

- implement more robust authentication
- set up pagination for the getEmployees endpoint
- user docker-compose to set up a real database
- add search and filter parameters
- allow field limiting
- error handling: I only handle a couple error cases but all operations against the database are fallible and so proper handling should be implemented
- create separate test config and test admin user

### Experience in Java

I've used Java as a secondary language at work for about 3 years. By secondary langauge, I
mean that it's not the language I use every day but from time to time I have to use it.
For example, at my current job our core application is written in Java. Mainly I work on
extensions, which are microservices (can use any language but usually TypeScript) and
microfrontends (TypeScript). Nonetheless, best practices in Java are the same in any language.
If I don't know how to do something in Java I still know what I need to know, so I can
look it up. This makes me slower than someone who uses Java every day, but it does not mean
my code is worse.
