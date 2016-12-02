package com.askanything.web.DAO;

import com.askanything.entitys.Tables.Authorities;
import com.askanything.entitys.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;


@Repository
@ComponentScan("com.askanything.conf.security")
public class HibernateUserDao implements UserDao,UserDetailsService {

    @PostConstruct
    private void oneTestUser(){
        User test = new User().setUsername("Vadim")
                .setEmail("lolEmail@mail.ri")
                .setEnabled(true)
                .setFirstName("Vadim")
                .setLastName("Ozar")
                .setPassword("1234");
        Authorities ath = new Authorities().setAuthority("ROLE_USER");
        ath.setUser(test);
        test.addAthority(ath);
        regNewUser(test);
    }

    @Inject
    SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean regNewUser(User user) {
        Transaction tr = null;
        try(Session session = sessionFactory.openSession()){
            tr = session.beginTransaction();
            session.persist(user);
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
            Query query = session.createQuery("from User where username = ?").setParameter(0, username);
            return (User) query.list().get(0);
        } catch (Exception ex){
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUserName(username);
    }
}
