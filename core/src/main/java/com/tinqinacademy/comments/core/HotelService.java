package com.tinqinacademy.comments.core;

import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentOutput;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomOutput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.models.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.models.usereditowncomment.UserEditOwnCommentOutput;

public interface HotelService {
    GetCommentsForRoomListOutput getCommentsForRoom(GetCommentsForRoomInput input);

    LeaveCommentForRoomOutput leaveCommentForRoom(LeaveCommentForRoomInput input);

    UserEditOwnCommentOutput userCanEditOwnComment(UserEditOwnCommentInput input);
}
