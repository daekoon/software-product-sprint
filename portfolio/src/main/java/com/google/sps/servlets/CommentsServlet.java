// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import com.google.sps.data.CommentsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {

  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  UserService userService = UserServiceFactory.getUserService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
    response.setContentType("application/json");

    
    Gson gson = new Gson();
    CommentsResponse resp;

    if (!userService.isUserLoggedIn()) {
      String loginURL = userService.createLoginURL("/comments.html");

      resp = new CommentsResponse(false, null, loginURL, null);

      String json = gson.toJson(resp);
      response.getWriter().println(json);
      return;
    }

    String logoutURL = userService.createLogoutURL("/index.html");

    List<Comment> comments = new ArrayList<Comment>();

    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String email = (String) entity.getProperty("email");
      String content = (String) entity.getProperty("content");

      Comment comment = new Comment(id, email, content);
      comments.add(comment);
    }

    resp = new CommentsResponse(true, comments, null, logoutURL);

    String json = gson.toJson(resp);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (!userService.isUserLoggedIn()) {
      String loginURL = userService.createLoginURL("/comments.html");
      response.sendRedirect(loginURL);
    }

    String content = request.getParameter("comment");
    long timestamp = System.currentTimeMillis();

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("email", userService.getCurrentUser().getEmail());
    commentEntity.setProperty("content", content);
    commentEntity.setProperty("timestamp", timestamp);

    datastore.put(commentEntity);

    response.sendRedirect("/comments.html");
  }
}