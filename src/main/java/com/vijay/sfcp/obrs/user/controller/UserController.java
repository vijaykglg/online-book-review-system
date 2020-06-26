package com.vijay.sfcp.obrs.user.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.common.utils.LogUtil;
import com.vijay.sfcp.obrs.error.exceptions.AlreadyExistsException;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import com.vijay.sfcp.obrs.user.dto.PasswordChangeRequest;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage(@RequestParam("role") String role,Model model) {
        String viewName = "register";
        if(!StringUtils.isEmpty(role)){
            if(role.equalsIgnoreCase("user"))
                role = "ROLE_USER";
            else if (role.equalsIgnoreCase("publisher")) {
                role = "ROLE_PUBLISHER";
                viewName = "addPublisher";
            }
            else if (role.equalsIgnoreCase("admin"))
                role = "ROLE_ADMIN";
            else
                role = "ROLE_USER";
        }else{
            role = "ROLE_USER";
        }

        User user = new User();
        model.addAttribute("user", user);
        return viewName;
    }

    @PostMapping("/registration")
    public String registerNewUser(@RequestParam("role") String role,@Valid User user, BindingResult bindingResult, Model model) throws AlreadyExistsException {
        String viewNameSuccess = "home";
        String viewNameError = "register";
        String message = "Welcome, You have successfully registered with Online Book review System. Please Login to continue.";
        LogUtil.logDebug(LOG,CLASS_NAME,"registerNewUser","user = " + user.toString()+" role = "+role);

        if(!StringUtils.isEmpty(role)){
            if(role.equalsIgnoreCase("user")) {
                viewNameSuccess = "redirect:/book/byCategory/?category=all&action=register";
                role = "ROLE_USER";
            }
            else if (role.equalsIgnoreCase("publisher")) {
                viewNameSuccess = "redirect:/user/byRole/?role="+role+"&action=register";
                viewNameError = "addPublisher";
                message = "Publisher added successfully with temporary password which you can share it with Publisher.";
                role = "ROLE_PUBLISHER";
            }
            else if (role.equalsIgnoreCase("admin"))
                role = "ROLE_ADMIN";
            else
                role = "ROLE_USER";
        }else{
            role = "ROLE_USER";
        }

        if (bindingResult.hasErrors()) {
            LogUtil.logError(LOG,CLASS_NAME,"registerNewUser","bindingResult = " + bindingResult.getAllErrors().toString());
            return viewNameError;
        }

        this.userService.registerNewUser(user, role);
        model.addAttribute("message",message);
        return viewNameSuccess;
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, Model model) {
        User user = this.userService.findById(id);
        model.addAttribute("user", user);
        return "update";
    }

    /*@GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> userList = (List<User>) this.userService.findAll();
        model.addAttribute("userList", userList);
        return "user";
    }*/

    @GetMapping("/")
    public String findAll(Model model) {
        List<User> userList = (List<User>) this.userService.findAll();
        model.addAttribute("userList", userList);
        return "user";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) throws NotFoundException {
        this.userService.deleteById(id);
        return "redirect:/users";// redirect the url
    }

    @GetMapping("/byRole/delete")
    public String delete(@RequestParam("id") Integer id) {
        this.userService.deleteById(id);
        return "redirect:/user/byRole/?role=publisher";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public User getPublisher(Integer id) {
        return this.userService.findById(id);
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) throws AlreadyExistsException, NotFoundException {
        LogUtil.logDebug(LOG,CLASS_NAME,"updateUser","user = " + user.toString());

        /*if (bindingResult.hasErrors()) {
            System.err.println("UserWebController.updateUser -  bindingResult = " + bindingResult.getAllErrors().toString());
            //showRegistrationScreen();
            return "update";
        }*/
        this.userService.saveOrUpdate(user);
        return "redirect:/users";// redirect the url
    }

    // Total control - setup a model and return the view name yourself. Or
    // consider subclassing ExceptionHandlerExceptionResolver (see below).
    @ExceptionHandler(AlreadyExistsException.class)
    public ModelAndView handleError(HttpServletRequest req, AlreadyExistsException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("errorMessage", ex.getErrorMessage());
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName("register");
        modelAndView.setStatus(HttpStatus.CONFLICT);
        modelAndView.addObject("user", new User());
        LogUtil.logError(LOG,CLASS_NAME,"handleError","view = register",ex);
        return modelAndView;
    }

    @GetMapping("/byRole")
    public String findUsersByRoles(@RequestParam("role") String role,@RequestParam(defaultValue = "user") String action,Model model) {
        String viewName = "user";
        LogUtil.logDebug(LOG,CLASS_NAME,"findUsersByRoles","role = " + role);
        if(!StringUtils.isEmpty(role)){
            if(role.equalsIgnoreCase("user"))
                role = "ROLE_USER";
            else if (role.equalsIgnoreCase("publisher")) {
                role = "ROLE_PUBLISHER";
                viewName = "publisher";
            }
            else if (role.equalsIgnoreCase("admin"))
                role = "ROLE_ADMIN";
            else
                role = "ROLE_USER";
        }else{
            role = "ROLE_USER";
        }
        List<User> userList = (List<User>) this.userService.findUsersByRoles(role);
        model.addAttribute("userList", userList);

        if(!StringUtils.isEmpty(action) && action.equalsIgnoreCase("register"))
            model.addAttribute("message","Publisher added successfully with temporary password which you can share it with Publisher.");

        return viewName;
    }

    @PostMapping("/actDeact")
    public String actDeact(@RequestParam("role") String role,@RequestParam("actDeact") String actDeact,Integer id) throws NotFoundException {
        LogUtil.logDebug(LOG,CLASS_NAME,"actDeact","role = " + role + ", actDeact = " + actDeact + ", id = " + id);

        if(this.userService.existsById(id)){
            User userById = this.userService.findById(id);
            if(!StringUtils.isEmpty(actDeact)){
                if(actDeact.equalsIgnoreCase("act"))
                    userById.setActive(true);
                else
                    userById.setActive(false);
            }
            this.userService.saveOrUpdate(userById);
        }else{
            throw new NotFoundException("User not Found");
        }
        return "redirect:/user/byRole/?role="+role;
    }

    /**
     * Get a user by its username.
     *
     * @param userName The username.
     * @return The user.
     */
    @GetMapping("/byUserName/")
    public User getUserByUsername(@RequestParam("userName") String userName) {
        User user = this.userService.findByUsername(userName);

        if (user == null) {
            throw new NotFoundException();
        }

        return user;
    }

    /**
     * Get the current user.
     *
     * @param principal The logged in user.
     * @return The current user.
     */
    @GetMapping("/me")
    public User getMe(Principal principal) {
        return getUserByUsername(principal.getName());
    }

    /**
     * Change the password of the logged in user.
     *
     * @param principal The logged in user.
     * @param password  The new raw password.
     */
    @PostMapping("/me/password")
    public String changePassword(Principal principal, @RequestBody @Valid PasswordChangeRequest password,Model model) {
        User user = this.userService.findByUsername(principal.getName());
        user.setPassword(password.getPassword());
        this.userService.saveOrUpdate(user);
        model.addAttribute("message","password Rest Success");
        return "passwordReset";
    }
}
