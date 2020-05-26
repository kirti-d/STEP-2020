
package com.google.sps.servlets;

import java.util.*;
import java.io.IOException;
import com.google.sps.data.comment;
import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addComment")
public final class addComment extends HttpServlet {
    
    // respond to post request by redirecting to index.html
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String comment = request.getParameter("comment");
        if(comment==null || comment.length()==0){
            response.setContentType("text/html;");
            response.getWriter().println("please enter a valid comment");
            return;
        }
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity commentEntity = new Entity("comment");
        commentEntity.setProperty("text", comment);
        datastore.put(commentEntity);
        response.sendRedirect("/index.html");
    }

    // respond to get request by returning json
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
     ArrayList<comment>commentList=new ArrayList();
     int totalComments=Integer.parseInt(request.getParameter("totalComments"));
        Query query = new Query("comment");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        int count=0;
        for (Entity entity : results.asIterable()) {
            if(count>=totalComments)
            break;
            long id = entity.getKey().getId();
            String text = (String) entity.getProperty("text");
            commentList.add(new comment(text,id));
            count++;
        }
        String json = convertToJson(commentList);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }
    // return json string of java object
    private String convertToJson(ArrayList<comment> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}