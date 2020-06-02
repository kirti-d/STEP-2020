
package com.google.sps.data;

import java.util.*;

// class for comment related info
public class Comment {
    String text;
    String image;
    long id;
    String user;

    public Comment(String text, long id, String user, String image) {
        this.text = text;
        this.id = id;
        this.user = user;
        this.image = image;
    }
}