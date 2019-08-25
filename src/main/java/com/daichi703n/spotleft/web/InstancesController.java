package com.daichi703n.spotleft.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daichi703n.spotleft.domain.SpotleftInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazonaws.Response;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Tag;

@Controller
@RequestMapping("/instances")
public class InstancesController {

    @RequestMapping(method = RequestMethod.GET)
    public String instances(Model model) {

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        boolean done = false;

        List<HashMap<String,String>> instances = new ArrayList<HashMap<String,String>>();

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for(Reservation reservation : response.getReservations()) {
                for(Instance instance : reservation.getInstances()) {
                    // System.out.printf(
                    //     "Found instance with id %s, " +
                    //     "AMI %s, " +
                    //     "type %s, " +
                    //     "state %s " +
                    //     "and monitoring state %s",
                    //     instance.getInstanceId(),
                    //     instance.getImageId(),
                    //     instance.getInstanceType(),
                    //     instance.getState().getName(),
                    //     instance.getMonitoring().getState());

                    HashMap<String,String> tHashMap = new HashMap<String,String>();
                    for(Tag tag : instance.getTags()){
                        if (tag.getKey() == null){break;}
                        switch (tag.getKey()){
                        case "Name":
                            tHashMap.put("InstanceName",tag.getValue());
                            continue;
                        case "deployment":
                            tHashMap.put("InstanceDeployment",tag.getValue());
                            continue;
                        default:
                            continue;
                        }
                            
                    }
                    tHashMap.put("InstanceId",instance.getInstanceId());
                    tHashMap.put("InstanceType",instance.getInstanceType());
                    tHashMap.put("InstanceLifeCycle",instance.getInstanceLifecycle());
                    tHashMap.put("InstanceState",instance.getState().getName());
                    tHashMap.put("InstanceLaunchTime",instance.getLaunchTime().toString());
                    instances.add(tHashMap);
                    SpotleftInfo spotleftInfo = new SpotleftInfo();
                    spotleftInfo.setId(instance.getInstanceId());
                    System.out.println(spotleftInfo.getId());
                }
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
            // System.out.println(response.toString());
            // instances.add(response.getReservations().getInstances());
        }


        model.addAttribute("msg",instances);
        return "instances/list";
    }

}
