package com.askanything.models.DAO;

import com.askanything.models.entitys.Tables.Question;
import com.askanything.models.entitys.User;

import java.util.List;

/**
 * Created by VadimOz on 05.11.16.
 */
public interface QuestionDAO {
    List<Question> getUnansweredQuestions(String username);

    List<Question> getAnsweredQuestions(User username);

    boolean askUser(String username, Question question);

    void answerQuestion(Question question);
}
