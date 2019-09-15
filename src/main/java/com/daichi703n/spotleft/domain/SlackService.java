package com.daichi703n.spotleft.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.seratch.jslack.*;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Field;
import com.github.seratch.jslack.api.webhook.*;

import org.springframework.stereotype.Service;

@Service
public class SlackService {

    public WebhookResponse hello() throws IOException {
        String url = System.getenv("SLACK_WEBHOOK_URL");
        
        Payload payload = Payload.builder()
        .text("Hello World!")
        .build();
        
        Slack slack = Slack.getInstance();
        WebhookResponse response = slack.send(url, payload);
        // response.code, response.message, response.body

        return response;
    }

    public WebhookResponse send(List<SpotleftInfo> instances) throws IOException {
        String url = System.getenv("SLACK_WEBHOOK_URL");
        
        List<Attachment> attachments = new ArrayList<Attachment>();

        instances.forEach(i -> {
            List<Field> fields = new ArrayList<>();
            fields.add(Field.builder()
                .title("deployment")
                .value(i.getDeployment())
                .valueShortEnough(true)
                .build());
            fields.add(Field.builder()
                .title("Name")
                .value(i.getName())
                .valueShortEnough(true)
                .build());
            fields.add(Field.builder()
                .title("Lifecycle")
                .value(i.getLifecycle())
                .valueShortEnough(true)
                .build());
            fields.add(Field.builder()
                .title("LaunchTime")
                .value(i.getLaunchTime())
                .valueShortEnough(true)
                .build());

            Attachment attachment = Attachment.builder()
                .color("warning")
                .fields(fields)
                .build();
            attachments.add(attachment);
        });

        
        Payload payload = Payload.builder()
            .text("Spotleft found illegal normal instance")
            .attachments(attachments)
            .build();
        
        Slack slack = Slack.getInstance();
        WebhookResponse response = slack.send(url, payload);
        // response.code, response.message, response.body

        return response;
    }
}
