# How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : <http://localhost:8080/swagger-ui.html>
- H2 UI : <http://localhost:8080/h2-console>

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

## Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests (DONE)
  - Change syntax (DONE)
  - Protect controller end points (DONE)
  - Add caching logic for database calls (DONE)
  - Improve doc and comments <-- TODO
  - Fix any bug you might find (DONE)
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

### Restrictions

- use java 8

### What we will look for

- Readability of your code
- Documentation
- Comments in your code
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years

## My Comments

### If I Had More Time

If I had more time I would...

- implement more robust authentication
- set up pagination for the getEmployees endpoint

### Experience in Java

I've used Java as a secondary language at work for about 3 years. By secondary langauge, I
mean that it's not the language I use every day but from time to time I have to use it.
For example, at my current job our core application is written in Java. Mainly I work on
extensions, which are microservices (can use any language but usually TypeScript) and
microfrontends (TypeScript). Nonetheless, best practices in Java are the same in any language.
If I don't know how to do something in Java I still know what I need to know, so I can
look it up. This makes me slower than someone who uses Java every day, but it does not mean
my code is worse.
