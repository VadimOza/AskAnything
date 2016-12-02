package com.askanything.web.DAO;

import com.askanything.entitys.Tables.Question;
import com.askanything.entitys.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 02.12.16.
 */
@Repository
public class HibernateQuestionDAO implements QuestionDAO{
    @Autowired
    UserDao userDao;

    @Inject
    SessionFactory sessionFactory;


    @Override
    public List<Question> getUnansweredQuestions(String username) {
        User user = userDao.getUserByUserName(username);
        List<Question> allQuestions = user.getQuestions();
        List<Question> unanswered = new ArrayList<>(allQuestions.size());
        for (Question q :
                allQuestions) {
            System.out.println("\n\n\n\n\n\n\n [" + q.getAnswer() + "] \n\n\n\n\n\n\n");
            if (q.getAnswer() == null || q.getAnswer().equals(""))
                unanswered.add(q);
        }
        return unanswered;
    }

    @Override
    public List<Question> getAnsweredQuestions(User user) {
        List<Question> questions = null;
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Question where user = ? and answer IS NOT NULL").setParameter(0, user);
            questions = query.list();
        }
        for (Question q :
                questions) {
            if (q.getAsker() == null)
                q.setAsker(new User().setUsername("ANONIMUS"));
        }
        return questions;
    }

    @Override
    public boolean askUser(String username, Question question) {
        User user = userDao.getUserByUserName(username);
        question.setDate(new Timestamp(System.currentTimeMillis()));
        user.getQuestions().add(question);
        question.setUser(user);
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        return true;
    }
}
