package com.daichi703n.spotleft.web.api.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daichi703n.spotleft.domain.AwsInstanceService;
import com.daichi703n.spotleft.domain.SlackService;
import com.daichi703n.spotleft.domain.SpotleftInfo;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/instances")
public class ApiInstanceController {

    @Autowired
    private AwsInstanceService awsInstanceService;

    @Autowired
    private SlackService slackService;

    @RequestMapping(method = RequestMethod.GET)
    public List<SpotleftInfo> instances() {
        List<SpotleftInfo> instances = awsInstanceService.findAll();
        return instances;
    }

    @RequestMapping(method = RequestMethod.GET, path = "illegal")
    public Map<String, List<SpotleftInfo>> illegalInstances() {

        List<SpotleftInfo> instances = awsInstanceService.findAll();
        List<SpotleftInfo> illegalInstances = new ArrayList<SpotleftInfo>();
        instances.forEach(i -> {
            log.debug(i.getDeployment());
            log.debug(i.getName());
            if (i.getDeployment() == null || i.getName() == null){
                return;
            }
            if (System.getenv("SPOTLEFT_EXCLUDE_NAME") != null && i.getName().contains(System.getenv("SPOTLEFT_EXCLUDE_NAME"))){
                log.info("Skip excluded name: {}", i.getName());
                return;
            }
            if (System.getenv("SPOTLEFT_EXCLUDE_TYPE") != null && i.getType().contains(System.getenv("SPOTLEFT_EXCLUDE_TYPE"))){
                log.info("Skip excluded type: {}", i.getType());
                return;
            }
            if (
                i.getRequireSpot()
                && !i.getLifecycle().equals("spot")
                && i.getState().equals("running")
            ){
                illegalInstances.add(i);
            }
        });
        log.debug("---generate and return map---");
        log.debug(illegalInstances.toString());
        Map<String, List<SpotleftInfo>> map = illegalInstances.stream()
            .filter(i -> i.getDeployment() != null)
            .filter(i -> i.getName() != null)
            .collect(Collectors.groupingBy(i -> i.getDeployment()));

        // return illegalInstances;
        return map;
    }

    @PostMapping("notify")
    public String instances(@RequestBody(required=false) String body) {

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
            // Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/instances";
    }

}
