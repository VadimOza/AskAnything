package com.askanything.entitys.Tables;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by root on 31.10.16.
 */
//@Entity
//@Table(name = "questions")
//@SecondaryTable(name="users")
public class Question {

    @Id
    @GeneratedValue
    long id;

    @Column(name = "question")
    String question;

    @Column(name = "date")
    Timestamp date;

    @Column(name = "answer")
    String answer;

    @Column(table = "users")
    String asker;

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getAnswer() {
        return answer;
    }

    public String getAsker() {
        return asker;
    }

    public Question setId(long id) {
        this.id = id;
        return this;
    }

    public Question setQuestion(String question) {
        this.question = question;
        return this;
    }

    public Question setDate(Timestamp date) {
        this.date = date;
        return this;
    }

    public Question setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Question setAsker(String asker) {
        this.asker = asker;
        return this;
    }
}
