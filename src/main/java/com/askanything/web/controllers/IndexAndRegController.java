package com.askanything.web.controllers;

import com.askanything.models.DAO.UserDao;
import com.askanything.models.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;


@Controller
@RequestMapping("/")
public class IndexAndRegController {

    private final UserDao userDao;

    @Autowired
    public IndexAndRegController(UserDao userDao) {
        this.userDao = userDao;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorrities = auth.getAuthorities();

        for (GrantedAuthority authorrity : authorrities) {
            if (authorrity.getAuthority().equals("ROLE_USER")) {
                return "redirect:/user/" + auth.getName();
            }
        }

        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String redirectOnUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "redirect:/user/" + auth.getName();

    }

    @RequestMapping(value = "/registration")
    public String registrationForm(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorrities = auth.getAuthorities();

        for (GrantedAuthority authorrity : authorrities) {
            if (authorrity.getAuthority().equals("ROLE_USER")) {
                model.addAttribute("user", userDao.getUserByUserName(auth.getName()));
                return "redirect:/user/" + auth.getName();
            }
        }

        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String regNewUser(@Valid User user, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "registration";

        return userDao.regNewUser(user) ? "redirect:/login" : "registration";
    }
}
