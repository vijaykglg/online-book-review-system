# Spring Framework Certification Project

## Online Book Review System

### Certification Project:

#### <ins>Problem Statement</ins>:

For certification, you have to build an Online Book Reviews System.

Before buying anything we see its reviews online to make sure we are buying the right thing. This application helps users to see the reviews about a book and a registered user can also submit their reviews about the book. The application should have following features:

* It should have Sign Up and Sign In functionality to allow users to login to your application.
* You should also handle session management.
* Registered users should be able to write a review for a book and all the users should be able to view the reviews for a book.
* While adding a book to database you should also provide tags that will help searching books. Like if you are adding a book on MongoDB your tags can be [DATABASE, NOSQL]. Your application should provide different search criteria like searching book by author name, by book title, by tags, by publisher.

Your application should also have Pagination functionality, because its very bad idea to load all the books together, you should only load 10-15 book at one time. When user clicks on page 2 you should display next 15 books like in google search results it shows 10 results, when you click on second page it displays next 10 results. To achieve this you should use ajax , jquery enabled datatables.

And the last thing your application should have is Facebook like box on your web application, which helps applications to get more likes on facebook. That will show how many users have already liked your application.

#### Advantages

- Customers can review the books online and can wish to buy based on the ratings/review.
- Managing of inventory in the shop for shopkeeper becomes easier as customers are not visiting and ordering online.
- This system saves both time and travelling cost of customers.
- User can get to know different kinds of books that they were unaware of by just searching in the system using keywords.

#### Disadvantages

- The only disadvantage is if the customer receives a book that is not in proper condition or has some kind of defect then there incurs an additional charge of posting it back.

## Application features

### Admin features

* Admin can register a new publisher or can assign publisher role to any user who wish to be a publisher.
* Admin can activate/deactivate any user/publisher.
* Can search books and give reviews and ratings.

### User features

* New users can register.
* Filter books based on tags/category.
* Advanced search on different parameters like title, author, publisher,category/tags etc.
* Get all books for a particular author.
* Get details about a book, including its reviews and ratings.
* Review any book based on your experience and give ratings.
* Users can like the facebook page of Online Book Review System.

### Publisher features

* Addition of books to database.
* Can create/update new category for books.
* Can add new authors and edit/update them if needed.
* Can search books and give reviews and ratings.

## Project Modules

* User Module
  - Login with spring security
  - Session management and role based access handling
* Role Module
  - Three defined roles
    * ROLE_ADMIN
    * ROLE_USER
    * ROLE_PUBLISHER
* Category/Tags Module
  - Various categories of books can be combined as a group like EDU/TECH etc..
* Author Module
  - Each books can have multiple authors
  - Publishers can add authors for books they published
* Book Module
   - Online book inventory for various categories.
   - Books image can be uploaded while adding/updating the book for better clarity.
   - Any user can search books based on interest
* Review Module
   - Users can view reviews online and cas see overall ratings of any book.
   - Users can review any book and can rate any book

## Implementation

* SpringBoot 2.3.0 is used in the backend.
* H2 databases used for storing and managing data.
* Thymeleaf used for a frontend.
* For testing purpose, data was scraped from various online sources including Amazon Online Book Store.


## Team
[![Vijay Kumar Gupta](https://avatars2.githubusercontent.com/u/57914226?s=400&u=711ef43ea909d33b585b75b406fa9a8189415a6b&v=4)](https://github.com/vijaykglg)

## License

Online Book review System © Vijay Gupta

## Version

* Version 1.0-SNAPSHOT