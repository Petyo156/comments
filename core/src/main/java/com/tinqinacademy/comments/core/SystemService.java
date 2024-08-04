package com.tinqinacademy.comments.core;

import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentOutput;

public interface SystemService {
    AdminEditAnyCommentOutput adminEditAnyComment(AdminEditAnyCommentInput input);

    AdminDeleteAnyCommentOutput adminDeleteAnyComment(AdminDeleteAnyCommentInput input);
}
