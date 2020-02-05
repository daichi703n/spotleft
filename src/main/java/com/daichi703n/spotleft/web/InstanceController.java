package com.daichi703n.spotleft.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.daichi703n.spotleft.domain.AwsInstanceService;
import com.daichi703n.spotleft.domain.SlackService;
import com.daichi703n.spotleft.domain.SpotleftInfo;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/instances")
public class InstanceController {

    @Autowired
    private AwsInstanceService awsInstanceService;

    @Autowired
    private SlackService slackService;

    @RequestMapping(method = RequestMethod.GET)
    public String instances(Model model) {

        List<SpotleftInfo> instances = awsInstanceService.findAll();

        model.addAttribute("instances",instances);
        return "instances/current";
    }

    @RequestMapping(method = RequestMethod.GET, path = "illegal")
    public String illegalInstances(Model model) {

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

        model.addAttribute("instances",illegalInstances);
        return "instances/current";
    }

    @PostMapping("notify")
    public String instances(@RequestBody(required=false) String body) {

        List<SpotleftInfo> instances = awsInstanceService.findAll();
        List<SpotleftInfo> notifyInstances = new ArrayList<SpotleftInfo>();

        log.info("Start Notify.");

        instances.forEach(i -> {
            if (
                i.getRequireSpot()
                && !i.getLifecycle().equals("spot")
                && i.getState().equals("running")
            ){
                notifyInstances.add(i);
            }
        });
        // System.out.println(notifyInstances);
        // notifyInstances.forEach(i -> {
        //     System.out.println(i.getName());
        //     System.out.println(i.getDeployment());
        //     System.out.println(i.getLaunchTime());
        // });
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
