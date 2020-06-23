package com.google.sps.data;

/**
 * Class that is used as a JSON Response to the /loginservice GET endpoint
 */
public class LoginResponse {
  private boolean loggedIn;
  private String loginURL;
  private String logoutURL;
  private String currentUserEmail;

  public LoginResponse(boolean loggedIn, String loginURL, String logoutURL, String currentUserEmail) {
      this.loggedIn = loggedIn;
      this.loginURL = loginURL;
      this.logoutURL = logoutURL;
      this.currentUserEmail = currentUserEmail;
  }
}