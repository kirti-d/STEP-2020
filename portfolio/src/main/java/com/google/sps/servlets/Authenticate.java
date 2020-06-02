package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@
WebServlet("/authenticate")
public final class Authenticate extends HttpServlet {
    private final UserService userService = UserServiceFactory.getUserService();

    //user login
    @
    Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        String urlToRedirectToAfterUserLogsIn = "/userName";
        String loginUrl = userService.createLoginURL(
            urlToRedirectToAfterUserLogsIn
        );
        response
            .getWriter()
            .println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }

    //return true if user is logged in else false
    @
    Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        if (userService.isUserLoggedIn()) {
            String json = convertToJson(true);
            response.setContentType("application/json");
            response.getWriter().println(json);
        } else {
            String json = convertToJson(false);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    // return json for java object
    private String convertToJson(Boolean status) {
        Gson gson = new Gson();
        String json = gson.toJson(status);
        return json;
    }
}