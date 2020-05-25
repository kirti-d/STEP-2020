
package com.google.sps.servlets;

import java.util.*;
import com.google.sps.data.comments;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addComment")
public final class addComment extends HttpServlet {
    
    public comments List=new comments();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String comment = request.getParameter("comment");
        if(comment==null || comment.length()==0){
            response.setContentType("text/html;");
            response.getWriter().println("please enter a valid comment");
            return;
        }
        
        List.commentList.add(comment);
        response.sendRedirect("/index.html");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = convertToJson(List);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }
    private String convertToJson(comments list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}