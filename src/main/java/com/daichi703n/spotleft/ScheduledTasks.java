package com.daichi703n.spotleft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.daichi703n.spotleft.domain.AwsInstanceService;
import com.daichi703n.spotleft.domain.SlackService;
import com.daichi703n.spotleft.domain.SpotleftInfo;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    AwsInstanceService awsInstanceService;

    @Autowired
    SlackService slackService;

    @Scheduled(cron = "${cron.cron1}")
    public void notifyInstances() {
        List<SpotleftInfo> instances = awsInstanceService.findAll();
        List<SpotleftInfo> notifyInstances = new ArrayList<SpotleftInfo>();

        instances.forEach(i -> {
            if (
                i.getRequireSpot()
                && !i.getLifecycle().equals("spot")
                && i.getState().equals("running")
            ){
                notifyInstances.add(i);
            }
        });
        try {
            WebhookResponse res = slackService.send(notifyInstances);
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
