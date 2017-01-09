package com.askanything.models.DAO;

import com.askanything.models.entitys.Tables.Authorities;
import com.askanything.models.entitys.User;
import com.askanything.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;


@Repository
@ComponentScan("com.askanything.conf.security")
public class HibernateUserDao implements UserDao, UserDetailsService {

    private final
    UserRepository userRepository;

    @Autowired
    public HibernateUserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void oneTestUser() {
        User firstUser = new User().setUsername("Vadim")
                .setEmail("lolEmail@mail.ri")
                .setEnabled(true)
                .setFirstName("Vadim")
                .setLastName("Ozar")
                .setPassword("1234");
        Authorities ath = new Authorities().setAuthority("ROLE_USER");
        ath.setUser(firstUser);
        firstUser.addAthority(ath);
        userRepository.save(firstUser);

        userRepository.save(new User().setUsername("Anonimus")
                .setEmail("someEmail@Watewer")
                .setEnabled(true)
                .setFirstName("Anonimus")
                .setLastName("Anonimusivich")
                .setPassword("qwerty1234"));
    }


    @Override
    public boolean regNewUser(User user) {
        user.setEnabled(true);
        Authorities ath = new Authorities().setAuthority("ROLE_USER");
        ath.setUser(user);
        user.addAthority(ath);
        userRepository.save(user);
        return true;
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUserName(username);
    }
}
