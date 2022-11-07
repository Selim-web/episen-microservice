package com.episen.membership.setting;

import com.episen.membership.model.StartupProperties;
import com.episen.membership.model.User;
import com.episen.membership.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationStartUp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StartupProperties startupProperties;

//    @Bean
//    public CommandLineRunner loadData() {
//        return (args) -> {
//            User adminUser = new User(startupProperties.getUsername(),
//                    BCrypt.hashpw(startupProperties.getPassword(), BCrypt.gensalt()),
//                    startupProperties.getEmail(),
//                    Arrays.asList("ADMIN"));
//            userRepository.add(adminUser);
//        };
//    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            User adminUser = new User("superadmin",
                   "superadmin",
                    "superadmin@episen.fr",
                    List.of("ADMIN"));
            userRepository.add(adminUser);
        };
    }


}
