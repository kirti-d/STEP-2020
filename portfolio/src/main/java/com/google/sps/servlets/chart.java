package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chart")
public class chart extends HttpServlet {

  // return json for hashmap containing each section count
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    HashMap<String, Long> sections = new HashMap<>();
    Query query = new Query("section");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    response.setContentType("application/json");
    for (Entity entity : results.asIterable()) {
      String section = (String) entity.getProperty("section");
      long count = (Long) entity.getProperty("count");
      sections.put(section, count);
    }
    Gson gson = new Gson();
    String json = gson.toJson(sections);
    response.getWriter().println(json);
  }

  // increase section count
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    String section = request.getParameter("section");
    Entity entity = getSectionCount(section);
    if (entity == null) {
      Entity sectionEntity = new Entity("section");
      sectionEntity.setProperty("section", section);
      sectionEntity.setProperty("count", 1);
      datastore.put(sectionEntity);
    } else {
      long currCount = (Long) entity.getProperty("count");
      entity.setProperty("count", currCount + 1);
      entity.setProperty("section", section);
      datastore.put(entity);
    }
    response.sendRedirect("/index.html");
  }

  //   function to return section entity
  private Entity getSectionCount(String section) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("section")
    .setFilter(
        new Query.FilterPredicate(
          "section",
          Query.FilterOperator.EQUAL,
          section
        )
      );
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    return entity;
  }
}
