package com.askanything.web.controllers;

import com.askanything.entitys.Tables.Question;
import com.askanything.entitys.User;
import com.askanything.exceptions.UserNotFoundException;
import com.askanything.web.DAO.QuestionDAO;
import com.askanything.web.DAO.UserDao;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by root on 21.10.16.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDAO questionDAO;

    @RequestMapping("/{username}")
    public String showUserPage(@PathVariable String username, Model model) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equalsIgnoreCase(currentUser)) {
            model.addAttribute("owner", true);
        }
        if (currentUser.equals("anonymousUser")) {
            model.addAttribute("anon", true);
        }
        model.addAttribute("question",new Question());
        User user = userDao.getUserByUserName(username);
        if (user != null) {
            model.addAttribute("user", user);
            return "user-page";
        }
        throw new UserNotFoundException();

    }

    @RequestMapping(value = "/{username}",method = RequestMethod.POST)
    public String showUserPagePost(@PathVariable String username,Question question){
        questionDAO.askUser(username,question);
        return "redirect:/user/"+username;
    }


    @RequestMapping("/answers")
    public String showAnswers(Model model) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Question> questions = questionDAO.getUnansweredQuestions(currentUser);
        model.addAttribute("questions",questions);
        return "answerit";
    }

}
