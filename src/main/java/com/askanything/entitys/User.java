package com.askanything.entitys;

import com.askanything.entitys.Tables.Authorities;
import com.askanything.entitys.Tables.Question;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id @GeneratedValue
    private long userId;

    @Column(name = "username")
    private String username;


    private String password;


    private String email;

    @Column(name = "fname")
    private String firstName;

    @Column(name = "lname")
    private String lastName;

    @OneToMany(cascade = javax.persistence.CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Authorities> authority = new HashSet<>();

    @OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();

    private boolean enabled;


    public List<Question> getQuestions() {
        return questions;
    }

    public User setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Set<Authorities> getAuthority() {
        return authority;
    }

    public User setAuthority(Set<Authorities> authority) {
        this.authority = authority;
        return this;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public User setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User() {
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    public void addAthority(Authorities ath){
        authority.add(ath);
    }
}
