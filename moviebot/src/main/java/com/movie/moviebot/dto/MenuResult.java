package com.movie.moviebot.dto;

import java.util.List;

public class MenuResult {
  public int page;
  public List<Result> results;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public List<Result> getResults() {
    return results;
  }

  public void setResults(List<Result> results) {
    this.results = results;
  }
}
