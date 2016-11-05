package com.askanything.entitys.Tables;

import javax.persistence.*;

/**
 * Created by root on 31.10.16.
 */
@Entity
@Table(name = "asks")
@SecondaryTables({@SecondaryTable(name="users"),
        @SecondaryTable(name = "questions")})
public class Asks {

    @Id
    @Column(table = "questions")
    long id;

    @Id
    @Column(table = "users")
    String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
