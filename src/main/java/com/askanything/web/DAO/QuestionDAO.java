package com.askanything.web.DAO;

import com.askanything.entitys.Tables.Question;

import java.util.List;

/**
 * Created by root on 05.11.16.
 */
public interface QuestionDAO {
    Question getQuetionByUsername(String username);
    List<Question> getUnansweredQuestions(String username);
    List<Question> getAnsweredQuestions(String username);
    boolean askUser(String username,Question question);
}
