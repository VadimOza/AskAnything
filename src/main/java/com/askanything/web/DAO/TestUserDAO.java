package com.askanything.web.DAO;

import com.askanything.entitys.User;
import com.askanything.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by root on 21.10.16.
 */


public class TestUserDAO implements UserDao {

    private Set<User> registeredUsers = new HashSet<>();

    @Override
    public boolean regNewUser(User user) {
        if (registeredUsers.contains(user)){
            System.out.printf("user " + user.getUsername() +" exist");
            return false;
        }
        registeredUsers.add(user);
        return true;
    }

    @Override
    public User getUserByUserName(String username) {
        Iterator<User> iter = registeredUsers.iterator();
        User user;
        while (iter.hasNext()){
            user = iter.next();
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
        //throw new UserNotFoundException();
    }
}
