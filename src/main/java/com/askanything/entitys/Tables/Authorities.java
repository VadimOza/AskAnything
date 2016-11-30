package com.askanything.entitys.Tables;

import com.askanything.entitys.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "authorities")
public class Authorities implements GrantedAuthority {

    @Id
    private long authId;

    @Column(name = "authority")
    private String authority;

    private String username;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public long getAuthId() {
        return authId;
    }

    public Authorities setAuthId(long authId) {
        this.authId = authId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Authorities setUsername(String username) {
        this.username = username;
        return this;
    }

    public Authorities setUser(User user) {
        this.user = user;
        username = user.getUsername();

        return this;
    }


    public String getAuthority() {
        return authority;
    }

    public Authorities setAuthority(String authority) {
        this.authority = authority;
        return this;

    }
}
