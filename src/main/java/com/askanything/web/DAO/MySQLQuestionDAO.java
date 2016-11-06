package com.askanything.web.DAO;

import com.askanything.entitys.Tables.Question;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 05.11.16.
 */
@Component
public class MySQLQuestionDAO implements QuestionDAO {

    @Autowired
    DataSource dataSource;

    @Override
    public Question getQuetionByUsername(String username) {
        return null;
    }

    @Override
    public List<Question> getUnansweredQuestions(String username) {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT q.question,q.asker,q.date FROM questions q inner join asks a ON q.id=a.questionId WHERE a.username=?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            List<Question> unansweredQuestions = new ArrayList<>();
            while (rs.next()){
                String s = rs.getString("asker");
                unansweredQuestions.add(new Question()
                        .setQuestion(rs.getString("question"))
                        .setAsker(s!=null ? s : "unnonimus"));
                System.out.println("\n\n\n\n\n\n"+rs.getString("question")+"\n\n\n\n\n\n\n");
            }
            return unansweredQuestions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Question> getAnsweredQuestions(String username) {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT q.question,q.answer,q.asker,q.date FROM questions q inner join asks a ON q.id=a.questionId " +
                    "WHERE a.username=? " +
                    "AND q.answer is not null");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            List<Question> unansweredQuestions = new ArrayList<>();
            while (rs.next()){
                String s = rs.getString("asker");
                unansweredQuestions.add(new Question()
                        .setQuestion(rs.getString("question"))
                        .setAnswer(rs.getString("answer"))
                        .setDate(rs.getTimestamp("date"))
                        .setAsker(s!=null ? s : "unnonimus"));
                System.out.printf("\n\n\n\n " + rs.getTimestamp("date") + "\n\n\n\n");
            }

            return unansweredQuestions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean askUser(String username, Question question) {
        Connection forRollBack = null;
        Savepoint save = null;
        try (Connection con = dataSource.getConnection()) {
            forRollBack = con;
            con.setAutoCommit(false);
            save = con.setSavepoint();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO questions(question,date) VALUE (?,?)");
            stmt.setString(1, question.getQuestion());

            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);

            stmt.setString(2, currentTime);
            stmt.execute();
            stmt = con.prepareStatement("SELECT id FROM questions WHERE question=?");
            stmt.setString(1, question.getQuestion());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt(1);

            stmt = con.prepareStatement("INSERT INTO asks(questionId,username) VALUE (?,?)");
            stmt.setString(1, Integer.toString(id));
            stmt.setString(2, username);
            stmt.execute();
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (forRollBack != null && save != null) try {
                forRollBack.rollback(save);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
