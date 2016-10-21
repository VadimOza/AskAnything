package com.askanything.web.DAO;

import com.askanything.entitys.User;
import com.askanything.exceptions.UserNotFoundException;

/**
 * Created by root on 21.10.16.
 */
public interface UserDao {
    boolean regNewUser(User user);

    User getUserByUserName(String username);
}
