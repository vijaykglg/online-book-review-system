package com.vijay.sfcp.obrs.bootstrap;

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.services.AuthorService;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.service.CategoryService;
import com.vijay.sfcp.obrs.publisher.service.PublisherService;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewId;
import com.vijay.sfcp.obrs.review.service.ReviewService;
import com.vijay.sfcp.obrs.role.entity.Role;
import com.vijay.sfcp.obrs.role.service.RoleService;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class SpringJpaBootstrap implements CommandLineRunner {
    private UserService userService;
    private RoleService roleService;
    private CategoryService categoryService;
    private ReviewService reviewService;
    private BookService bookService;
    private AuthorService authorService;
    private PublisherService publisherService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
        loadRoles();
        loadCategory();
        loadAuthors();
        loadBooks();
        loadReviews();

        assignUsersToUserRole();
        assignUsersToAdminRole();
        assignUsersToPublisherRole();
        assignAuthorToBook();
        assignCategoryToBook();
        assignPublisherToBook();
       /* assignReviewsToUser();
        assignBookToReview();
        assignUserToReview();*/
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUserName("user");
        user1.setPassword("user");
        user1.setFirstName("UserFirstName");
        user1.setLastName("UserLastname");
        user1.setEmail("abc@test.com");
        user1.setDescription("user");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        user1 = new User();
        user1.setUserName("user1");
        user1.setPassword("user1");
        user1.setFirstName("UserFirstName1");
        user1.setLastName("UserLastname1");
        user1.setEmail("abc1@test.com");
        user1.setDescription("user1");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        user1 = new User();
        user1.setUserName("user2");
        user1.setPassword("user2");
        user1.setFirstName("UserFirstName2");
        user1.setLastName("UserLastname2");
        user1.setEmail("abc2@test.com");
        user1.setDescription("user2");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        user1 = new User();
        user1.setUserName("user3");
        user1.setPassword("user3");
        user1.setFirstName("UserFirstName3");
        user1.setLastName("UserLastname3");
        user1.setEmail("abc3@test.com");
        user1.setDescription("user3");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        user1 = new User();
        user1.setUserName("user4");
        user1.setPassword("user4");
        user1.setFirstName("UserFirstName4");
        user1.setLastName("UserLastname4");
        user1.setEmail("abc4@test.com");
        user1.setDescription("user4");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        user1 = new User();
        user1.setUserName("user5");
        user1.setPassword("user5");
        user1.setFirstName("UserFirstName5");
        user1.setLastName("UserLastname5");
        user1.setEmail("abc5@test.com");
        user1.setDescription("user5");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        user1 = new User();
        user1.setUserName("user6");
        user1.setPassword("user6");
        user1.setFirstName("UserFirstName6");
        user1.setLastName("UserLastname6");
        user1.setEmail("abc6@test.com");
        user1.setDescription("user6");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        User user2 = new User();
        user2.setUserName("admin");
        user2.setPassword("admin");
        user2.setFirstName("AdminFirstName");
        user2.setLastName("AdminLastname");
        user2.setEmail("xyz@test.com");
        user2.setDescription("admin");
        userService.saveOrUpdate(user2);
        System.out.println("Saved User2 " + user2.getUserName());

        User user3 = new User();
        user3.setUserName("publisher");
        user3.setPassword("publisher");
        user3.setFirstName("publisherFirstName");
        user3.setLastName("PublisherLastname");
        user3.setEmail("pqr@test.com");
        user3.setDescription("XYZ Publications");
        userService.saveOrUpdate(user3);
        System.out.println("Saved user3 " + user3.getUserName());

        User user4 = new User();
        user4.setUserName("publisher1");
        user4.setPassword("publisher");
        user4.setFirstName("VK");
        user4.setLastName("G");
        user4.setEmail("abc@test.com");
        user4.setDescription("ABC Publications");
        userService.saveOrUpdate(user4);
        System.out.println("Saved user4 " + user4.getUserName());
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("ROLE_USER");
        roleService.saveOrUpdate(role);
        System.out.println("Saved role " + role.getRole());

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleService.saveOrUpdate(adminRole);
        System.out.println("Saved role " + adminRole.getRole());

        Role publisherRole = new Role();
        publisherRole.setRole("ROLE_PUBLISHER");
        roleService.saveOrUpdate(publisherRole);
        System.out.println("Saved role " + publisherRole.getRole());
    }

    private void loadCategory() {
        Category category = new Category();
        category.setName("SPRL");
        category.setDescription("Spiritual");

        categoryService.saveOrUpdate(category);

        category = new Category();
        category.setName("TECH");
        category.setDescription("Technology");

        categoryService.saveOrUpdate(category);

        category = new Category();
        category.setName("SCI");
        category.setDescription("Science");

        categoryService.saveOrUpdate(category);

        category = new Category();
        category.setName("EDU");
        category.setDescription("Education");
        categoryService.saveOrUpdate(category);

        category = new Category();
        category.setName("POLS");
        category.setDescription("Politics");
        categoryService.saveOrUpdate(category);

        category = new Category();
        category.setName("ENV");
        category.setDescription("Environment");
        categoryService.saveOrUpdate(category);

        System.out.println("Saved Category ");
    }

    private void loadAuthors() {
        Author author = new Author();
        author.setName("Rakesh");
        author.setDescription("Top Author");
        authorService.saveOrUpdate(author);

        author = new Author();
        author.setName("John");
        author.setDescription("Top Author");
        authorService.saveOrUpdate(author);

        author = new Author();
        author.setName("Thompson");
        author.setDescription("Top Author");
        authorService.saveOrUpdate(author);

        author = new Author();
        author.setName("Rod");
        author.setDescription("Top Author");
        authorService.saveOrUpdate(author);

        System.out.println("Saved Authors");
    }

    private void loadReviews() {
        List<Book> books = (List<Book>) bookService.findAll();
        List<User> users = (List<User>) userService.findAll();

        users.forEach(user -> {
            books.forEach(book -> {
                if(book.getTitle().contains("ABC")){
                    Review review = new Review();
                    review.setRating(user.getId());
                    review.setReviewText("Review by " + user.getUserName());
                    review.setId(new ReviewId(book, user));

                    reviewService.saveOrUpdate(review);
                    System.out.println("reviewService updated");
                }
            });
        });
        System.out.println("Saved Reviews");
    }

    private void loadBooks() {
        Random random = new Random(9999999);
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setIsbn(String.valueOf(random.nextInt()));
            book.setTitle("BOOK_ABC" + i);
            book.setReleaseDate("16/03/2020");
            book.setDescription("Some quick example text to build on the card title and make up the bulk of the card's content.");
            bookService.saveOrUpdate(book);
        }
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setIsbn(String.valueOf(random.nextInt()));
            book.setTitle("BOOK_XYZ" + i);
            book.setReleaseDate("16/03/2020");
            book.setDescription("Some quick example text to build on the card title and make up the bulk of the card's content.");
            bookService.saveOrUpdate(book);
        }
        System.out.println("Saved Books ");
    }

    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.findAll();
        List<User> users = (List<User>) userService.findAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ROLE_USER")) {
                users.forEach(user -> {
                    if (user.getUserName().contains("user")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.findAll();
        List<User> users = (List<User>) userService.findAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
                users.forEach(user -> {
                    if (user.getUserName().equals("admin")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    private void assignUsersToPublisherRole() {
        List<Role> roles = (List<Role>) roleService.findAll();
        List<User> users = (List<User>) userService.findAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ROLE_PUBLISHER")) {
                users.forEach(user -> {
                    if (user.getUserName().equals("publisher")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    private void assignCategoryToBook() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        List<Book> books = (List<Book>) bookService.findAll();

        categories.forEach(category -> {
            if (category.getName().equalsIgnoreCase("TECH")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("ABC")) {
                        book.setCategory(category);
                        bookService.saveOrUpdate(book);
                    }
                });
            }

            if (category.getName().equalsIgnoreCase("EDU")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("XYZ")) {
                        book.setCategory(category);
                        bookService.saveOrUpdate(book);
                    }
                });
            }
        });
    }

    private void assignAuthorToBook() {
        System.out.println("SpringJpaBootstrap.assignAuthorToBook");
        List<Author> authors = (List<Author>) authorService.findAll();
        List<Book> books = (List<Book>) bookService.findAll();

        authors.forEach(author -> {
            if (author.getName().equalsIgnoreCase("John")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("ABC")) {
                        book.setAuthor(author);
                        bookService.saveOrUpdate(book);
                    }
                });
            }

            if (author.getName().equalsIgnoreCase("Rod")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("XYZ")) {
                        book.setAuthor(author);
                        bookService.saveOrUpdate(book);
                    }
                });
            }
        });
        System.out.println("Assigned Authors to Book");
    }

    private void assignPublisherToBook() {
        List<User> publishers = (List<User>) userService.findAll();
        List<Book> books = (List<Book>) bookService.findAll();

        publishers.forEach(publisher -> System.out.println(publisher.toString()));
        publishers.forEach(publisher -> {
            if (publisher.getDescription().contains("ABC")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("ABC")) {
                        book.setPublishers(new HashSet<User>((Arrays.asList(publisher))));
                        bookService.saveOrUpdate(book);
                    }
                });
            }

            if (publisher.getDescription().contains("XYZ")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("XYZ")) {
                        book.setPublishers(new HashSet<User>((Arrays.asList(publisher))));
                        bookService.saveOrUpdate(book);
                    }
                });
            }
        });
    }

    /*private void assignReviewsToUser() {
        List<User> users = (List<User>) userService.findAll();
        List<Review> reviews = reviewService.findAll();

        reviews.forEach(review -> {
            users.forEach(user -> {
                if (user.getRoles().contains("ROLE_USER")) {
                    user.addReview(review);
                    userService.saveOrUpdate(user);
                }
            });
        });
        System.out.println("assignReviewsToUser");
    }

    private void assignBookToReview() {
        System.out.println("SpringJpaBootstrap.assignBookToReview");
        List<Review> reviews = (List<Review>) reviewService.findAll();
        List<Book> books = (List<Book>) bookService.findAll();


        reviews.forEach(review -> {
            if (review.getRating().compareTo(BigDecimal.valueOf(3.0)) >0) {
                books.forEach(book -> {
                    if (book.getTitle().contains("ABC")) {
                        book.addReview(review);
                        bookService.saveOrUpdate(book);
                    }
                });
            }

            if (review.getRating().compareTo(BigDecimal.valueOf(5.0)) >0) {
                books.forEach(book -> {
                    if (book.getTitle().contains("XYZ")) {
                        book.addReview(review);
                        bookService.saveOrUpdate(book);
                    }
                });
            }
        });
        System.out.println("Assigned Book to Review");
    }

    private void assignUserToReview() {
        System.out.println("SpringJpaBootstrap.assignBookToReview");
        List<Review> reviews = (List<Review>) reviewService.findAll();
        List<User> users = (List<User>) userService.findAll();

        reviews.forEach(review -> {
            if (review.getRating().compareTo(BigDecimal.valueOf(3.0)) >0) {
                users.forEach(user -> {
                    if (user.getUserName().equalsIgnoreCase("user")) {
                        user.addReview(review);
                        userService.saveOrUpdate(user);
                    }
                });
            }

            if (review.getRating().compareTo(BigDecimal.valueOf(5.0)) >0) {
                users.forEach(user -> {
                    if (user.getUserName().equalsIgnoreCase("user")) {
                        user.addReview(review);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
        System.out.println("Assigned User to Review");
    }*/
}
