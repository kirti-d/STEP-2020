package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.*;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@
WebServlet("/userName")
public final class UserName extends HttpServlet {
    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final UserService userService = UserServiceFactory.getUserService();

    // add user entity to datastore
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        String userName = request.getParameter("userName");
        String id = userService.getCurrentUser().getUserId();

        Entity user = new Entity("user", id);
        user.setProperty("userName", userName);
        user.setProperty("id", id);
        datastore.put(user);
        response.sendRedirect("/");
    }

    // present user with a form to add userName
    @
    Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>Set your userName here:</p>");
        out.println("<form method=\"POST\" action=\"/userName\">");
        out.println("<input name=\"userName\" value=\" anonymous \" />");
        out.println("<br/>");
        out.println("<button>Submit</button>");
        out.println("</form>");
    }
}