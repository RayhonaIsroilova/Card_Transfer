package ecma.ai.transferapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyAuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> userList = new ArrayList<>(
                Arrays.asList(
                        new User("admin", passwordEncoder.encode("admin"), new ArrayList<>()),
                        new User("user", passwordEncoder.encode("user"), new ArrayList<>()),
                        new User("manager", passwordEncoder.encode("manager"), new ArrayList<>())
                ));
        for (User user : userList) {
            if (user.getUsername().equals(s)) return user;
        }
        throw new UsernameNotFoundException("User topilmadi aka!");
    }
}
