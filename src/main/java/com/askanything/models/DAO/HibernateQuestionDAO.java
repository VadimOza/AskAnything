package com.askanything.models.DAO;

import com.askanything.models.repositories.QuestionRepository;
import com.askanything.models.repositories.UserRepository;
import com.askanything.models.entitys.Tables.Question;
import com.askanything.models.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by VadimOz on 02.12.16.
 */
@Repository
public class HibernateQuestionDAO implements QuestionDAO{

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    @Autowired
    public HibernateQuestionDAO(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Question> getUnansweredQuestions(String username) {

        User userFromDB = userRepository.findOneByUsername(username);
        return userFromDB.getQuestions().stream().filter(q -> q.getAnswer() == null).collect(Collectors.toList());
    }

    @Override
    public List<Question> getAnsweredQuestions(User user) {
        User userFromDB = userRepository.findOneByUsername(user.getUsername());
        return userFromDB.getQuestions().stream()
                .filter(q -> q.getAnswer() != null)
                .map(q->q.setAsker(new User().setUsername("Anonumis")))//Todo create reference to real asker
                .collect(Collectors.toList());
    }

    @Override
    public boolean askUser(String username, Question question) {

        User user = userRepository.findOneByUsername(username);
        question.setDate(new Timestamp(System.currentTimeMillis()));

        user.getQuestions().add(question);
        question.setUser(user);
        userRepository.save(user);

        return true;
    }

    @Override
    public void answerQuestion(Question question) {
        User user = userRepository.findOneByUsername(question.getUser().getUsername());
        List<Question> questions = user.getQuestions();

        int idx = questions.indexOf(question);

        questions.get(idx).setAnswer(question.getAnswer());
        userRepository.save(user);

    }
}
