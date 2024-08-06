package com.tinqinacademy.comments.persistence.initialize;

import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

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
        if (commentsRepository.count() == 0) {
            commentsRepository.save(Comment.builder()
                    .content("petar e gotin")
                    .roomId(UUID.fromString("0d1f2fa8-86ad-4639-b221-f654a6581519"))
                    .lastName("udri")
                    .firstName("sirene")
                    .lastEditDate(LocalDate.now())
                    .creationDate(LocalDate.now())
                    .build());
        }

        log.info("Fully working!!!");
    }
}