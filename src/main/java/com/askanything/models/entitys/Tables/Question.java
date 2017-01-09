package com.askanything.models.entitys.Tables;

import com.askanything.models.entitys.User;
import com.askanything.web.Services.CustomerDateAndTimeDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by VadimOz on 31.10.16.
 */
@Entity
@Table(name = "questions")
public class Question{

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "question")
    private String question;

    @JsonDeserialize(using = CustomerDateAndTimeDeserialize.class)
    @Column(name = "date")
    private Date date;

    @Column(name = "answer")
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User asker;

    public User getAsker() {
        return asker;
    }

    public Question() {
    }

    public Question setAsker(User asker) {
        this.asker = asker;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Date getDate() {
        return date;
    }

    public String getAnswer() {
        return answer;
    }

    public User getUser() {
        return user;
    }

    public Question setId(long id) {
        this.id = id;
        return this;
    }

    public Question setQuestion(String question) {
        this.question = question;
        return this;
    }

    public Question setDate(Date date) {
        this.date = date;
        return this;
    }

    public Question setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Question setUser(User asker) {
        this.user = asker;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) &&
                Objects.equals(date, question1.date) &&
                Objects.equals(user, question1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, date, user);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", date=" + date +
                ", answer='" + answer + '\'' +
                ", user=" + user +
                ", asker=" + asker +
                '}';
    }
}
