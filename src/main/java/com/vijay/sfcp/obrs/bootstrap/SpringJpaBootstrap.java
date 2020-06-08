package com.vijay.sfcp.obrs.bootstrap;

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.services.AuthorService;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.service.CategoryService;
import com.vijay.sfcp.obrs.publisher.entity.Publisher;
import com.vijay.sfcp.obrs.publisher.service.PublisherService;
import com.vijay.sfcp.obrs.review.entity.Review;
import com.vijay.sfcp.obrs.review.entity.ReviewPK;
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
        loadPublisher();
        loadBooks();
        //loadReviews();

        assignUsersToUserRole();
        assignUsersToAdminRole();
        assignUsersToPublisherRole();
        assignAuthorToBook();
        assignCategoryToBook();
        //assignPublisherToBook();
        /*assignReviewsToUser();
        assignReviewsToBook();*/
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUserName("user");
        user1.setPassword("user");
        user1.setFirstName("UserFirstName");
        user1.setLastName("UserLastname");
        user1.setEmail("abc@test.com");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        User user2 = new User();
        user2.setUserName("admin");
        user2.setPassword("admin");
        user2.setFirstName("AdminFirstName");
        user2.setLastName("AdminLastname");
        user2.setEmail("xyz@test.com");
        userService.saveOrUpdate(user2);
        System.out.println("Saved User2 " + user2.getUserName());

        User user3 = new User();
        user3.setUserName("publisher");
        user3.setPassword("publisher");
        user3.setFirstName("publisherFirstName");
        user3.setLastName("PublisherLastname");
        user3.setEmail("pqr@test.com");
        userService.saveOrUpdate(user3);
        System.out.println("Saved user3 " + user3.getUserName());
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

    private void loadPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("XYZ PUBLICATION");
        publisher.setDescription("TOP In the City");
        publisherService.saveOrUpdate(publisher);

        publisher = new Publisher();
        publisher.setName("ABC PUBLICATION");
        publisher.setDescription("TOP In the India");
        publisherService.saveOrUpdate(publisher);
        System.out.println("Saved Publisher ");
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
        List<Role> roles = (List<Role>) roleService.findAll();

        /*roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ROLE_USER")) {
                System.out.println("SpringJpaBootstrap.loadReviews - ROLE is USER");
                users.forEach(user -> {
                         books.forEach(book -> {
                            Review review = new Review();
                            review.setRating(new BigDecimal("5.3"));
                            review.setReviewText("Review by User");
                            review.setId(new ReviewPK(user.getId(),book.getIsbn()));
                            reviewService.saveOrUpdate(review);

                           *//* book.addReview(review);
                            user.addReview(review);*//*
                        });
                });
            }
        });*/
        System.out.println("SpringJpaBootstrap.loadReviews - Saved Review");
        /*users.forEach(user -> {
            if(user.getRoles().contains("USER"))
            {
                books.forEach(book -> {
                    Review review = new Review();
                    review.setRating(new BigDecimal("5.3"));
                    review.setReviewText("Review by User");
                    review.setId(new ReviewPK(book.getIsbn(),user.getId()));
                    reviewService.saveOrUpdate(review);

                    book.addReview(review);
                    user.addReview(review);
                });
            }
        });*/

/*
        Review review = new Review();
        review.setRating(new BigDecimal("5.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("7.0"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("8.9"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("2.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("4.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("1.5"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("9.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("10.0"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("6.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("3.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("2.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("1.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());

        review = new Review();
        review.setRating(new BigDecimal("7.3"));
        review.setReviewText("Review by User");
        reviewService.saveOrUpdate(review);
        System.out.println("Saved Review " + review.getRating());*/

    }

    private void loadBooks() {
        Random random = new Random(9999999);
        for (int i = 0; i < 100; i++) {
            Book book = new Book();
            book.setIsbn(String.valueOf(random.nextInt()));
            book.setTitle("BOOK_ABC"+i);
            //book.setReleaseDate("16/03/2020");
            bookService.saveOrUpdate(book);
        }
        for (int i = 0; i < 100; i++) {
            Book book = new Book();
            book.setIsbn(String.valueOf(random.nextInt()));
            book.setTitle("BOOK_XYZ"+i);
            //book.setReleaseDate("16/03/2020");
            bookService.saveOrUpdate(book);
        }

        /*List<Publisher> publishers = (List<Publisher>) publisherService.findAll();
        List<Book> books = (List<Book>) bookService.findAll();
        publishers.forEach(publisher -> {
            if (publisher.getName().contains("ABC")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("ABC")) {
                        book.addPublisher(publisher);
                    }
                });
            }

            if (publisher.getName().contains("XYZ")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("XYZ")) {
                        book.addPublisher(publisher);
                    }
                });
            }
        });*/
        System.out.println("Saved Books ");
    }

    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.findAll();
        List<User> users = (List<User>) userService.findAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ROLE_USER")) {
                users.forEach(user -> {
                    if (user.getUserName().equals("user")) {
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

    /*private void assignReviewsToUser() {
        List<Review> reviews = (List<Review>) reviewService.listAll();
        List<User> users = (List<User>) userService.findAll();

        users.forEach(user -> {
            if(user.getRoles().contains("USER"))
            {
                reviews.forEach(review -> {
                    user.addReview(review);
                    userService.saveOrUpdate(user);
                });
            }
        });
    }

    private void assignReviewsToBook() {
        List<Review> reviews = (List<Review>) reviewService.listAll();
        List<Book> books = (List<Book>) bookService.findAll();

        books.forEach(book -> {
            reviews.forEach(review -> {
                book.addReview(review);
                bookService.saveOrUpdate(book);
            });
        });
    }
*/
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
    }

    private void assignPublisherToBook() {
        List<Publisher> publishers = (List<Publisher>) publisherService.findAll();
        List<Book> books = (List<Book>) bookService.findAll();

        publishers.forEach(publisher -> {
            if (publisher.getName().contains("ABC")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("ABC")) {
                        book.setPublishers(new HashSet<>((Arrays.asList(publisher))));
                        bookService.saveOrUpdate(book);
                    }
                });
            }

            if (publisher.getName().contains("XYZ")) {
                books.forEach(book -> {
                    if (book.getTitle().contains("XYZ")) {
                        book.setPublishers(new HashSet<>((Arrays.asList(publisher))));
                        bookService.saveOrUpdate(book);
                    }
                });
            }
        });
    }
}
