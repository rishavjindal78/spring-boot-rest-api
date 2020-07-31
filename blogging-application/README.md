# Blogging-Application

## below is the maven command to build an application
mvn clean install

## Story 1 : Create an article

Using Postman you can create an article
	POST URL :http://localhost:8080/blogs/save

Add below json into body tab
{
  "title": "How to learn Spring Booot2",
  "description": "Ever wonder how2?",
  "body": "You have to believe2",
}

Add below header into headers tab
createdBy: "user"

## Story 2: Update an article

Here is the URL to update the article
	PUT URL: http://localhost:8080/blogs/update/{id} 

Add below header into headers tab
createdBy: "user"

Add below json into body tab
{ 
    "title": "How to learn Spring Boot by building an app"
} 

## Story 3: Get an article

Here is the URL to get the article
 GET URL:: http://localhost:8080/blogs/{id}

## Story 4: List all articles

Here is the URL to get the article
 GET ALL URL: http://localhost:8080/blogs/all?page=0&size=2
 
## Story 5: User can only update articles that they created
Here is the URL to get the article
 	PUT URL: http://localhost:8080/blogs/update/{id} 

Add below header into headers tab
createdBy: "user"

Add below json into body tab
{ 
    "title": "How to learn Spring Boot by building an app"
} 
## Story 6: User can only delete articles that they created
Here is the URL to get the article
 	DELETE URL: http://localhost:8080/blogs/{id}
	
Add below header into headers tab
createdBy: "user"

## Story 7: Find all articles by a User

	GET ALL by user:: http://localhost:8080/blogs/all/{user}?page=0&size=2

## To access H2 database console
http://localhost:8080/h2-console
JDBC URL: "jdbc:h2:mem:testdb"
username: sa
password: sa

