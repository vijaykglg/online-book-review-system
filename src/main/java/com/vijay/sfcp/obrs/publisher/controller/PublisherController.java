package com.vijay.sfcp.obrs.publisher.controller;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 01 June 2020
*/

import com.vijay.sfcp.obrs.author.entity.Author;
import com.vijay.sfcp.obrs.publisher.entity.Publisher;
import com.vijay.sfcp.obrs.publisher.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/")
    public String showPublishers(Model model) {
        List<Publisher> publisherList = (List<Publisher>) this.publisherService.findAll();
        model.addAttribute("publisherList", publisherList);
        return "publisher";
    }

    @PostMapping("/save")
    public String save(Publisher publisher) {
        this.publisherService.saveOrUpdate(publisher);
        return "redirect:/publisher/";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        this.publisherService.deleteById(id);
        return "redirect:/publisher/";
    }

    @GetMapping("/{id}")
    public String getPublisher(@PathVariable("id") Integer id, Model model){
        Publisher publisher = this.publisherService.findById(id);
        model.addAttribute("publisher",publisher);
        return "publisher";
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Publisher getPublisher(Integer id) {
        return this.publisherService.findById(id);
    }
}
