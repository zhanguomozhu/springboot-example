package com.dyq.springboot.validation;

import com.dyq.springboot.validation.domain.User;
import com.dyq.springboot.validation.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidationApplication implements CommandLineRunner {
    private Logger LOG = LoggerFactory.getLogger(ValidationApplication.class);

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ValidationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Sergey", 24, "1994-01-01");
        User user2 = new User("Ivan", 26, "1994-01-01");
        User user3 = new User("Adam", 31, "1994-01-01");
        LOG.info("Inserting data in DB.");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        LOG.info("User count in DB: {}", userRepository.count());
        LOG.info("User with ID 1: {}", userRepository.findById(1L));
        LOG.info("Deleting user with ID 2L form DB.");
        userRepository.deleteById(2L);
        LOG.info("User count in DB: {}", userRepository.count());
    }
}
