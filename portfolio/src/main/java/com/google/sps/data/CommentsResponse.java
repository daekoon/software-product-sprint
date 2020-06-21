package com.google.sps.data;

import java.util.List;

/**
 * Class that is used as a JSON Response to the /comments GET endpoint
 */

public class CommentsResponse {
    private boolean loggedIn;
    private List<Comment> comments;
    private String loginURL;
    private String logoutURL;
    private String currentUserEmail;

    public CommentsResponse(
            boolean loggedIn,
            List<Comment> comments,
            String loginURL,
            String logoutURL,
            String currentUserEmail) {
        this.loggedIn = loggedIn;
        this.comments = comments;
        this.loginURL = loginURL;
        this.logoutURL = logoutURL;
        this.currentUserEmail = currentUserEmail;
    }
    
}