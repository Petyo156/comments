package com.tinqinacademy.comments.persistence.initialize;

import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CustomApplicationRunner implements ApplicationRunner {
    //We’ll want to use an ApplicationRunner if we need to create
    // some global startup logic with access to complex command-line arguments.

    private final CommentsRepository commentsRepository;

    @Autowired
    public CustomApplicationRunner(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Fully working!!!");
    }
}