package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentOutput;

public interface SystemService {
    AdminEditAnyCommentOutput adminEditAnyComment(AdminEditAnyCommentInput input);

    AdminDeleteAnyCommentOutput adminDeleteAnyComment(AdminDeleteAnyCommentInput input);
}
