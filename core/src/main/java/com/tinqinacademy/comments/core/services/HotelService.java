package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;

public interface HotelService {
    GetCommentsForRoomListOutput getCommentsForRoom(GetCommentsForRoomInput input);

    LeaveCommentForRoomOutput leaveCommentForRoom(LeaveCommentForRoomInput input);

    UserEditOwnCommentOutput userCanEditOwnComment(UserEditOwnCommentInput input);
}
