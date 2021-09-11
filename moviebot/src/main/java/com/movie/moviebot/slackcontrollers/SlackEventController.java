package com.movie.moviebot.slackcontrollers;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;

import javax.servlet.annotation.WebServlet;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.externalSelect;
import static com.slack.api.model.view.Views.view;

@WebServlet("/slack/events")
public class SlackEventController extends SlackAppServlet {
  public SlackEventController(App app) {
    super(app);
    // When user opens the home tab on appBuld a view with external select so movie options can be loaded from client
    app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
      View appHomeView =
          view(view ->
                   view.type("home").blocks(asBlocks(
                       section(section ->
                                   section.blockId("app-command")
                                       .accessory(
                                           externalSelect(
                                               externalSelectElementBuilder ->
                                                   externalSelectElementBuilder.actionId(("app-command"))
                                                       .placeholder(plainText("Select a movie"))
                                                       .minQueryLength(3)))
                                       .text(markdownText("Select A Movie"))
                          )
                      )
                   )
          );


      ctx.client().viewsPublish(r -> r
          .userId(payload.getEvent().getUser())
          .view(appHomeView)
      );

      return ctx.ack();
    });
  }
}