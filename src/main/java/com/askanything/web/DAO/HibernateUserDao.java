package com.askanything.web.DAO;

import com.askanything.entitys.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


//@Repository
//@ComponentScan("com.askanything.conf.security")
public class HibernateUserDao implements UserDao {

    @Inject
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean regNewUser(User user) {
        Transaction tr = null;
        try(Session session = sessionFactory.openSession()){
            tr = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex) {
            if(tr!=null) tr.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByUserName(String username) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(User.class, username);
        } catch (Exception ex){
            return null;
        }
    }
}
