package com.askanything.web.DAO;

import com.askanything.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;



//@Component
public class MySqlDAO implements UserDao {

    @Autowired
    DataSource dataSource;

    @Override
    public boolean regNewUser(User user) {

        Statement request;
        PreparedStatement stmt;
        PreparedStatement insertRole;
        try ( Connection con = dataSource.getConnection()) {
            request = con.createStatement();
            stmt = con.prepareStatement("insert into users(username, password, fname, lname, email,enabled) value(?,?,?,?,?,?)");
            ResultSet rs = request.executeQuery("select username from users");
            insertRole = con.prepareStatement("insert into authorities(username,authority) value(?,?)");
            if (rs.getFetchSize() == 0){
                stmt.setString(1,user.getUsername());
                stmt.setString(2,user.getPassword());
                stmt.setString(3,user.getFirstName());
                stmt.setString(4,user.getLastName());
                stmt.setString(5,user.getEmail());
                stmt.setBoolean(6,true);
                insertRole.setString(1,user.getUsername());
                insertRole.setString(2,"ROLE_USER");
                stmt.execute();
                insertRole.execute();

                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByUserName(String username) {
        PreparedStatement request;
        try ( Connection con = dataSource.getConnection()) {
            request = con.prepareStatement("select username,password, fname, lname, email from users where username = ?");
            request.setString(1,username);
            ResultSet rs = request.executeQuery();
            if( rs.next() ){
                return new User().setUsername(username)
                        .setEmail(rs.getString("email"))
                        .setFirstName(rs.getString("fname"))
                        .setLastName(rs.getString("lname"))
                        .setPassword(rs.getString("password"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
