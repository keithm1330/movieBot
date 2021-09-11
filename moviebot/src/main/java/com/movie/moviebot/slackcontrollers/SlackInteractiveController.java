package com.movie.moviebot.slackcontrollers;

import com.movie.moviebot.client.RestClient;
import com.movie.moviebot.dto.MovieInforResult;
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@WebServlet("/slack/get-movie-information")
public class SlackInteractiveController extends SlackAppServlet {
  private RestClient rc = new RestClient();

  public SlackInteractiveController(App app) {
    super(app);
    /*
    When movie is selected build a block and send the details and movie poster back to channel
     */
    app.blockAction("app-command", (req, ctx) -> {
      String value = req.getPayload().getActions().get(0).getSelectedOption().getValue(); // "button's value"
      MovieInforResult movieDetails = rc.getMovieInformation(value);
      List <LayoutBlock> message = new ArrayList();
      message.add(
          SectionBlock.builder()
              .text(
                  MarkdownTextObject.builder()
                      .text("Here is the movie information you requested for : " + movieDetails.getOriginal_title()).build())
                      .build());
      message.add(
          SectionBlock.builder()
              .text(
                  MarkdownTextObject.builder()
                      .text("Here is the movie information you requested for : " + movieDetails.getOverview()).build())
              .build());

      message.add(
          ImageBlock.builder()
              .imageUrl("https://image.tmdb.org/t/p/w500/" + movieDetails.getPoster_path())
                .altText(movieDetails.getPoster_path())
              .build());

      ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(ctx.getRequestUserId()).blocks(message).build();

      ctx.client().chatPostMessage(request);
      return ctx.ack();
    });

    /*
    Load the movie results  when user types in at least 3 letters
     */
    app.blockSuggestion("app-command", (req, ctx) -> {
      String keyword = req.getPayload().getValue();
      //Use Stream to filter matching keyword from button
      List <Option> options = rc.getMovieMenuOptions(keyword).stream().filter(o -> ((PlainTextObject) o.getText()).getText().contains(keyword)).collect(toList());
      return ctx.ack(r -> r.options(options.isEmpty() ? new ArrayList < Option > () : options));
    });
  }
}