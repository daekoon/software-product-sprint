package com.google.sps.data;

public class Comment {
  private long id;
  private String email;
  private String content;

  public Comment(long id, String email, String content) {
    this.id = id;
    this.email = email;
    this.content = content;
  }

  public long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getContent() {
    return this.content;
  }
}