package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.LoginResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/loginservice")
public class LoginServlet extends HttpServlet{
  UserService userService = UserServiceFactory.getUserService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String redirectPath = request.getParameter("redirect_path");
    response.setContentType("application/json");
    Gson gson = new Gson();
    LoginResponse resp;

    if (!userService.isUserLoggedIn()) {
      String loginURL = userService.createLoginURL(redirectPath);
      resp = new LoginResponse(false, loginURL, null);
    } else {
      String logoutURL = userService.createLogoutURL(redirectPath);
      resp = new LoginResponse(true, null, logoutURL);
    }
    
    String json = gson.toJson(resp);
    response.getWriter().println(json);
  }
}