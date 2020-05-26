package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import com.google.sps.data.comment;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteComment")
public final class deleteComment extends HttpServlet {

  // delete comment from datastore
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    long id = Long.parseLong(request.getParameter("id"));
    Key commentEntityKey = KeyFactory.createKey("comment", id);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.delete(commentEntityKey);
  }
}
