package com.askanything.web.controllers;

import com.askanything.exceptions.UserNotFoundException;
import com.askanything.models.DAO.QuestionDAO;
import com.askanything.models.DAO.UserDao;
import com.askanything.models.entitys.Tables.Question;
import com.askanything.models.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by VadimOz on 21.10.16.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserDao userDao;

    private final QuestionDAO questionDAO;

    @Autowired
    public UserController(UserDao userDao, QuestionDAO questionDAO) {
        this.userDao = userDao;
        this.questionDAO = questionDAO;
    }

    @RequestMapping("/{username}")
    public String showUserPage(@PathVariable String username, Model model) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equalsIgnoreCase(currentUser)) {
            model.addAttribute("owner", true);
        }
        if (currentUser.equals("anonymousUser")) {
            model.addAttribute("anon", true);
        }

        User user = userDao.getUserByUserName(username);
        if (user != null) {
            model.addAttribute("user", user);
            int answers = 0;
            for (Question q :
                    user.getQuestions()) {
                if (q.getAnswer() != null)
                    answers++;
            }
            model.addAttribute("answeres", answers);
            model.addAttribute("answeredQuestions", questionDAO.getAnsweredQuestions(user));
            return "user-page";
        }
        throw new UserNotFoundException();

    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public String showUserPagePost(@PathVariable String username, @RequestParam("question") String question, @RequestParam(value = "anon", required = false) String anon) {

        Question newQuestion = new Question().setQuestion(question);

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (anon == null && !currentUser.equals("anonymousUser"))
            newQuestion.setAsker(userDao.getUserByUserName(currentUser));
        questionDAO.askUser(username, newQuestion);
        return "redirect:/user/" + username;
    }


    @RequestMapping("/answers")
    public String showAnswers(Model model) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Question> questions = questionDAO.getUnansweredQuestions(currentUser);
        model.addAttribute("questions", questions);
        model.addAttribute("countOfQuestions", questions.size());
        return "answerit";
    }


    @RequestMapping(value = "/asynkAnswers", method = RequestMethod.POST)
    @ResponseBody
    public String answerItAsynk(@RequestBody Question question) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        question.setUser(userDao.getUserByUserName(currentUser));
        questionDAO.answerQuestion(question);
        return "{}";
    }


}
