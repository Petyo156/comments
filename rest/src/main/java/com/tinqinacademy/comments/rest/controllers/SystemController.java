package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.apimapping.RestApiMappingComments;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.core.processors.service.AdminDeleteAnyCommentOperationProcessor;
import com.tinqinacademy.comments.core.processors.service.AdminEditAnyCommentOperationProcessor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemController extends BaseController {

    private final ObjectMapper objectMapper;

    private final AdminEditAnyCommentOperationProcessor adminEditAnyCommentOperationProcessor;
    private final AdminDeleteAnyCommentOperationProcessor adminDeleteAnyCommentOperationProcessor;

    @Autowired
    public SystemController(ObjectMapper objectMapper, AdminEditAnyCommentOperationProcessor adminEditAnyCommentOperationProcessor,
                            AdminDeleteAnyCommentOperationProcessor adminDeleteAnyCommentOperationProcessor) {
        this.adminEditAnyCommentOperationProcessor = adminEditAnyCommentOperationProcessor;
        this.adminDeleteAnyCommentOperationProcessor = adminDeleteAnyCommentOperationProcessor;

        this.objectMapper = objectMapper;
    }

    @PutMapping(RestApiMappingComments.PUT_PATH)
    public ResponseEntity<?> adminEditAnyComment(
            @Valid @RequestBody AdminEditAnyCommentInput input,
            @PathVariable("commentId") String commentId) {

        AdminEditAnyCommentInput adminEditAnyCommentInput = input.toBuilder()
                .commentId(commentId)
                .build();

        return handleOperation(adminEditAnyCommentOperationProcessor.process(adminEditAnyCommentInput), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(RestApiMappingComments.DELETE_PATH)
    public ResponseEntity<?> adminDeleteAnyComment(
            @Valid @RequestBody AdminDeleteAnyCommentInput input,
            @PathVariable("commentId") String commentId) {

        AdminDeleteAnyCommentInput adminDeleteAnyCommentInput = input.toBuilder()
                .commentId(commentId)
                .build();

        return handleOperation(adminDeleteAnyCommentOperationProcessor.process(adminDeleteAnyCommentInput), HttpStatus.BAD_REQUEST);
    }
}
