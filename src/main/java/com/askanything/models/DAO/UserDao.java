package com.askanything.models.DAO;

import com.askanything.models.entitys.User;

/**
 * Created by VadimOz on 21.10.16.
 */
public interface UserDao {
    boolean regNewUser(User user);

    User getUserByUserName(String username);
}
