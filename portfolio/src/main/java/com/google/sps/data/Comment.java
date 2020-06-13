package com.google.sps.data;

public class Comment {
  private long id;
  private String username;
  private String content;

  public Comment(long id, String username, String content) {
    this.id = id;
    this.username = username;
    this.content = content;
  }

  public long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getContent() {
    return this.content;
  }
}