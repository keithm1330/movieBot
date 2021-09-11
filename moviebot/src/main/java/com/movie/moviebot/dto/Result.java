package com.movie.moviebot.dto;

import java.util.List;

public class Result {

  public long id;
  public String original_title;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }
}
