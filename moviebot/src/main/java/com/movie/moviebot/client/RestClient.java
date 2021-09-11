package com.movie.moviebot.client;

import com.movie.moviebot.dto.MenuResult;
import com.movie.moviebot.dto.MovieInforResult;
import com.slack.api.app_backend.interactive_components.response.Option;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

public class RestClient {

  private String LOAD_MOVIE_OPTIONS_URL = "https://api.themoviedb.org/3/search/movie?en-US&page=1&include_adult=false&query=";
  private String LOAD_MOVIE_INFO = "https://api.themoviedb.org/3/movie/";


  private RestTemplate rs = new RestTemplate();

  public List<Option> getMovieMenuOptions(String val) {
    List<Option> optionsList = new ArrayList<>();
    Objects.requireNonNull(rs.exchange(setupUrl(LOAD_MOVIE_OPTIONS_URL, val), HttpMethod.GET, new HttpEntity<Object>(setupHeaders()), MenuResult.class).getBody()).getResults().stream().forEach(a -> optionsList.add(new Option(plainText(a.original_title, true), String.valueOf(a.id))));
    return optionsList;
  }

  public MovieInforResult getMovieInformation(String val) {
    ResponseEntity<MovieInforResult> entity = rs.exchange(setupUrl(LOAD_MOVIE_INFO, val), HttpMethod.GET, new HttpEntity<Object>(setupHeaders()), MovieInforResult.class);
    return entity.getBody();
  }

  /**
   * Util method to setup correct headers for calling Movie API
   * @return HttpHeaders object with correct Bearear Token
   */
  private HttpHeaders setupHeaders(){
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYmFiNDM1OGIwMjNhYmNkNDQ4ZWFjNWVlMWM3MzJkZSIsInN1YiI6IjYxMzM2YzI2MWZiOTRmMDA2MTNlNzFhZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xq5CAEbRZutIv1RG3Z6cU0fm84Ql312UrNBUDKsaZcI");
    return headers;
  }

  /**
   * Util method to construct Movie API url
   * @param baseUrl baseurl for api
   * @param apiParam param to pass to api
   * @return
   */
  private String setupUrl(String baseUrl, String apiParam){
    StringBuilder url = new StringBuilder(baseUrl);
    url.append(apiParam);
    return url.toString();
  }
}
