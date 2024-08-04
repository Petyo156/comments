package com.tinqinacademy.comments.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentOutput;
import com.tinqinacademy.comments.core.SystemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemController {
    private SystemService systemService;
    private ObjectMapper objectMapper;

    @Autowired
    public SystemController(SystemService systemService, ObjectMapper objectMapper) {
        this.systemService = systemService;
        this.objectMapper = objectMapper;
    }

    @PutMapping(RestApiMapping.PUT_PATH)
    public ResponseEntity<AdminEditAnyCommentOutput> adminEditAnyComment(
            @Valid @RequestBody AdminEditAnyCommentInput input,
            @PathVariable("commentId") String commentId) {

        AdminEditAnyCommentInput input1 = input.toBuilder()
                .commentId(commentId)
                .build();

        return new ResponseEntity<>(systemService.adminEditAnyComment(input1), HttpStatus.OK);
    }

    @DeleteMapping(RestApiMapping.DELETE_PATH)
    public ResponseEntity<AdminDeleteAnyCommentOutput> adminDeleteAnyComment(
            @Valid @RequestBody AdminDeleteAnyCommentInput input,
            @PathVariable("commentId") String commentId) {

        AdminDeleteAnyCommentInput input1 = input.toBuilder()
                .commentId(commentId)
                .build();

        return new ResponseEntity<>(systemService.adminDeleteAnyComment(input1), HttpStatus.OK);
    }
}
