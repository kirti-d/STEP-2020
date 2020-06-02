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
WebServlet("/deleteComment")
public final class DeleteComment extends HttpServlet {
    private final UserService userService = UserServiceFactory.getUserService();
    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // delete comment from datastore
    @
    Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        // if user is not loggedin the comment can't be deleted
        if (!userService.isUserLoggedIn()) {
            return;
        }
        long id = Long.parseLong(request.getParameter("id"));

        // a user cannot delete comment by another user
        if (id != Long.parseLong(userService.getCurrentUser().getUserId())) return;
        Key commentEntityKey = KeyFactory.createKey("comment", id);
        datastore.delete(commentEntityKey);
    }
}