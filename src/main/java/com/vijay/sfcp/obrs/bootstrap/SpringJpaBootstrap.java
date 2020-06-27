package com.vijay.sfcp.obrs.bootstrap;

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.author.service.AuthorService;
import com.vijay.sfcp.obrs.book.entity.Book;
import com.vijay.sfcp.obrs.book.service.BookService;
import com.vijay.sfcp.obrs.category.entity.Category;
import com.vijay.sfcp.obrs.category.service.CategoryService;
import com.vijay.sfcp.obrs.common.service.StorageService;
import com.vijay.sfcp.obrs.common.service.StorageServiceImpl;
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

import javax.annotation.Resource;
import java.io.File;
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
    @Resource
    StorageService storageService;

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
        loadRoles();
        loadUsers();
        loadCategory();
        loadAuthors();
        loadBooks();
        loadReviews();

        /*assignUsersToUserRole();
        assignUsersToAdminRole();
        assignUsersToPublisherRole();*/
        assignRolesToUser();
        assignAuthorToBook();
        assignCategoryToBook();
        assignPublisherToBook();
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUserName("user");
        user1.setPassword("user");
        user1.setFirstName("Vihan");
        user1.setLastName("Joshi");
        user1.setEmail("abc@test.com");
        user1.setDescription("Vihan have the USER role");
        userService.saveOrUpdate(user1);
        System.out.println("Saved User1 " + user1.getUserName());

        User user2 = new User();
        user2.setUserName("admin");
        user2.setPassword("admin");
        user2.setFirstName("Vijay");
        user2.setLastName("Gupta");
        user2.setEmail("xyz@test.com");
        user2.setDescription("Vijay have the ADMIN role");
        userService.saveOrUpdate(user2);
        System.out.println("Saved User2 " + user2.getUserName());

        User user3 = new User();
        user3.setUserName("publisher");
        user3.setPassword("publisher");
        user3.setFirstName("Dev");
        user3.setLastName("Kumar");
        user3.setEmail("pqr@test.com");
        user3.setDescription("XYZ Publications-Dev have the PUBLISHER role");
        userService.saveOrUpdate(user3);
        System.out.println("Saved user3 " + user3.getUserName());
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("ROLE_USER");
        role.setDescription("A normal user who can search and review books");
        roleService.saveOrUpdate(role);
        System.out.println("Saved role " + role.getRole());

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        role.setDescription("An admin who can add publisher, assign roles to users and activate or deactivate any user/publisher");
        roleService.saveOrUpdate(adminRole);
        System.out.println("Saved role " + adminRole.getRole());

        Role publisherRole = new Role();
        publisherRole.setRole("ROLE_PUBLISHER");
        role.setDescription("A publisher can create new category/author and add new books");
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
        author.setName("Munshi Premchand");
        author.setDescription("An Indian writer famous for his modern Hindustani literature");
        authorService.saveOrUpdate(author);

        author = new Author();
        author.setName("Bhishma Sahani");
        author.setDescription("Bhisham Sahni was an Indian writer, playwright in Hindi and an actor, most famous for his novel and television screenplay");
        authorService.saveOrUpdate(author);

        author = new Author();
        author.setName("Rod Johnson");
        author.setDescription("Roderick (Rod) Johnson is an Australian computer specialist who created the Spring Framework and co-founded SpringSource");
        authorService.saveOrUpdate(author);

        author = new Author();
        author.setName("Craig Walls");
        author.setDescription("Craig Walls is a principal software engineer at Pivotal, a popular author, an enthusiastic supporter of Spring Framework and voice-first applications, and a frequent conference speaker");
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

   /* private void assignUsersToUserRole() {
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
    }*/

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

    private void assignRolesToUser() {
        List<User> users = (List<User>) userService.findAll();
        List<Role> roles = (List<Role>) roleService.findAll();

        roles.forEach(role -> {
            if (role.getRole().contains("ROLE_USER")) {
                users.forEach(user -> {
                    if (user.getUserName().contains("user")) {
                        user.setRoles(new HashSet<Role>((Arrays.asList(role))));
                        userService.saveOrUpdate(user);
                    }
                });
            }

            if (role.getRole().contains("ROLE_PUBLISHER")) {
                users.forEach(user -> {
                    if (user.getUserName().contains("publisher")) {
                        user.setRoles(new HashSet<Role>((Arrays.asList(role))));
                        userService.saveOrUpdate(user);
                    }
                });
            }

            if (role.getRole().contains("ROLE_ADMIN")) {
                users.forEach(user -> {
                    if (user.getUserName().contains("admin")) {
                        user.setRoles(new HashSet<Role>((Arrays.asList(role))));
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
}
