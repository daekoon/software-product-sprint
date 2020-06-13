package com.google.sps.data;

public class Comment {
  public long id;
  public String username;
  public String content;

  public Comment(long id, String username, String content) {
    this.id = id;
    this.username = username;
    this.content = content;
  }
}