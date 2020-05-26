package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.comment;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public final class logout extends HttpServlet {

  // logout link
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    String urlToRedirectToAfterUserLogsOut = "/";
    UserService userService = UserServiceFactory.getUserService();
    String logoutUrl = userService.createLogoutURL(
      urlToRedirectToAfterUserLogsOut
    );
    response.setContentType("text/HTML");
    response
      .getWriter()
      .println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
  }
}
