package com.google.sps.servlets;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.images.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.comment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.activation.DataSource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addComment")
public final class addComment extends HttpServlet {

  // respond to post request by redirecting to comments.jsp
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    UserService userService = UserServiceFactory.getUserService();

    // if the user is not authenticated don't let him add anything
    if (!userService.isUserLoggedIn()) {
      return;
    }
    String comment = request.getParameter("comment");
    if (comment == null || comment.length() == 0) {
      response.setContentType("text/html;");
      response.getWriter().println("please enter a valid comment");
      return;
    }
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    String id = userService.getCurrentUser().getUserId();
    String imageUrl = getUploadedFileUrl(request, "image");
    Entity commentEntity = new Entity("comment");
    commentEntity.setProperty("text", comment);
    commentEntity.setProperty("user", id);
    commentEntity.setProperty("userName", getuserName(id));
    commentEntity.setProperty("image", imageUrl);

    datastore.put(commentEntity);
    response.sendRedirect("/comments.jsp");
  }

  // respond to get request by returning json
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    ArrayList<comment> commentList = new ArrayList();
    int totalComments = Integer.parseInt(request.getParameter("totalComments"));
    Query query = new Query("comment");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    int count = 0;
    for (Entity entity : results.asIterable()) {
      if (count >= totalComments) break;
      long id = entity.getKey().getId();
      String text = (String) entity.getProperty("text");
      String name = (String) entity.getProperty("userName");
      String image = (String) entity.getProperty("image");
      commentList.add(new comment(text, id, name, image));
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

  // get userName
  private String getuserName(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("user")
    .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
      return "";
    }
    String userName = (String) entity.getProperty("userName");
    return userName;
  }

  private String getUploadedFileUrl(
    HttpServletRequest request,
    String formInputElementName
  ) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");
    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }
    BlobKey blobKey = blobKeys.get(0);

    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    if (blobInfo.getSize() == 0) {
      blobstoreService.delete(blobKey);
      return null;
    }
    ImagesService imagesService = ImagesServiceFactory.getImagesService();
    ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
    try {
      URL url = new URL(imagesService.getServingUrl(options));
      return url.getPath();
    } catch (MalformedURLException e) {
      return imagesService.getServingUrl(options);
    }
  }
}
