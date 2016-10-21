package com.askanything.web.controllers;

import com.askanything.entitys.User;
import com.askanything.exceptions.UserNotFoundException;
import com.askanything.web.DAO.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 21.10.16.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/{username}")
    public String showUserPage(@PathVariable String username, Model model){
        User user = userDao.getUserByUserName(username);
        if (user!=null) {
            model.addAttribute("user", user);
            return "user-page";
        }
        throw new UserNotFoundException();
    }

    @RequestMapping("/answers")
    public String showAnswers(){
        return "answerit";
    }

}
