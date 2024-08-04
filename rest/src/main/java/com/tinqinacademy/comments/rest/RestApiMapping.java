package com.tinqinacademy.comments.rest;

public class RestApiMapping {
    //hotel
    public static final String GET_PATH = "/hotel/{roomId}/comment";
    public static final String POST_PATH = "/hotel/{roomId}/comment";
    public static final String PATCH_PATH = "/hotel/comment/{commentId}";


    //system
    public static final String PUT_PATH = "/system/comment/{commentId}";
    public static final String DELETE_PATH = "/system/comment/{commentId}";
}
