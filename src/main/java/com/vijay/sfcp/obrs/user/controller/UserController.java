package com.vijay.sfcp.obrs.user.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.error.exceptions.AlreadyExistsException;
import com.vijay.sfcp.obrs.error.exceptions.NotFoundException;
import com.vijay.sfcp.obrs.user.entity.User;
import com.vijay.sfcp.obrs.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

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
    public String saveUser(@RequestParam("role") String role,@Valid User user, BindingResult bindingResult, Model model) throws AlreadyExistsException {
        String viewNameSuccess = "home";
        String viewNameError = "register";
        String message = "Welcome, You have successfully registered with Online Book review System. Please Login to continue.";
        System.out.println("UserWebController.saveUser - user = " + user.toString()+" role = "+role);

        if(!StringUtils.isEmpty(role)){
            if(role.equalsIgnoreCase("user"))
                role = "ROLE_USER";
            else if (role.equalsIgnoreCase("publisher")) {
                viewNameSuccess = "redirect:/user/byRole/?role="+role;
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
            System.err.println("UserWebController.saveUser -  bindingResult = " + bindingResult.getAllErrors().toString());
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
        System.out.println("UserWebController.updateUser - user = " + user.toString());
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
        System.out.println("UserController.handleError - view = register , errorMessage = "+ex.getErrorMessage());
        return modelAndView;
    }

    @GetMapping("/byRole")
    public String findUsersByRoles(@RequestParam("role") String role,Model model) {
        String viewName = "user";
        System.out.println("role = " + role);
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
        return viewName;
    }
}
