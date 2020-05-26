package com.google.sps.data;

import java.util.*;

// class for comment related info
public class comment {
  String text;
  long id;
  String user;

  public comment(String text, long id, String user) {
    this.text = text;
    this.id = id;
    this.user = user;
  }
}
