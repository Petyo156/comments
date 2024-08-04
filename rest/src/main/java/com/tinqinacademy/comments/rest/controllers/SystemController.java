package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentOutput;
import com.tinqinacademy.comments.core.processors.service.AdminDeleteAnyCommentOperationProcessor;
import com.tinqinacademy.comments.core.processors.service.AdminEditAnyCommentOperationProcessor;
import com.tinqinacademy.comments.core.services.SystemService;
import com.tinqinacademy.comments.rest.config.RestApiMapping;
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

    @PutMapping(RestApiMapping.PUT_PATH)
    public ResponseEntity<?> adminEditAnyComment(
            @Valid @RequestBody AdminEditAnyCommentInput input,
            @PathVariable("commentId") String commentId) {

        AdminEditAnyCommentInput input1 = input.toBuilder()
                .commentId(commentId)
                .build();

        //return new ResponseEntity<>(systemService.adminEditAnyComment(input1), HttpStatus.OK);
        return handleOperation(adminEditAnyCommentOperationProcessor.process(input1), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(RestApiMapping.DELETE_PATH)
    public ResponseEntity<?> adminDeleteAnyComment(
            @Valid @RequestBody AdminDeleteAnyCommentInput input,
            @PathVariable("commentId") String commentId) {

        AdminDeleteAnyCommentInput input1 = input.toBuilder()
                .commentId(commentId)
                .build();

        //return new ResponseEntity<>(systemService.adminDeleteAnyComment(input1), HttpStatus.OK);
        return handleOperation(adminDeleteAnyCommentOperationProcessor.process(input1), HttpStatus.BAD_REQUEST);
    }
}
