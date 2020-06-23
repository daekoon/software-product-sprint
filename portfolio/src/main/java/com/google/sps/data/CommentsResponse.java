package com.google.sps.data;

import java.util.List;

/**
 * Class that is used as a JSON Response to the /comments GET endpoint
 */

public class CommentsResponse {
    private List<Comment> comments;

    public CommentsResponse(List<Comment> comments) {
        this.comments = comments;
    } 
}