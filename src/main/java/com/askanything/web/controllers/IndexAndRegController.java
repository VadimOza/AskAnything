package com.askanything.web.controllers;

import com.askanything.entitys.User;
import com.askanything.exceptions.UserNotFoundException;
import com.askanything.web.DAO.UserDao;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 19.10.16.
 */
@Controller
@RequestMapping("/")
public class IndexAndRegController {

    public IndexAndRegController() {
    }

    @Autowired
    private UserDao userDao;

    public IndexAndRegController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String redirectOnUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/user/" +  auth.getName();
    }

    @RequestMapping(value = "/registration")
    public String registrationForm(Model model) {
        model.addAttribute(new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String regNewUser(User user) {
        return userDao.regNewUser(user) ? "redirect:/user/" + user.getUsername() : "registration";
    }
}
