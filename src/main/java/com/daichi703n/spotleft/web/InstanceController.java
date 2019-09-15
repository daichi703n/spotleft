package com.daichi703n.spotleft.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daichi703n.spotleft.domain.SavedInstance;
import com.daichi703n.spotleft.domain.SavedInstanceService;
import com.daichi703n.spotleft.domain.SpotleftInfo;

import org.springframework.beans.factory.annotation.Autowired;
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
public class InstanceController {

    @Autowired
    private SavedInstanceService savedInstanceService;

    @RequestMapping(method = RequestMethod.GET)
    public String instances(Model model) {

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        boolean done = false;

        List<SpotleftInfo> instances = new ArrayList<SpotleftInfo>();
        List<SavedInstance> savedInstances = savedInstanceService.findAll();

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

                    SpotleftInfo spotleftInfo = new SpotleftInfo();
                    for(Tag tag : instance.getTags()){
                        if (tag.getKey() == null){break;}
                        switch (tag.getKey()){
                        case "Name":
                            spotleftInfo.setName(tag.getValue());
                            continue;
                        case "deployment":
                        spotleftInfo.setDeployment(tag.getValue());
                            continue;
                        default:
                            continue;
                        }
                            
                    }
                    spotleftInfo.setInstanceId(instance.getInstanceId());
                    spotleftInfo.setType(instance.getInstanceType());
                    spotleftInfo.setLifeCycle(instance.getInstanceLifecycle());
                    spotleftInfo.setState(instance.getState().getName());
                    spotleftInfo.setLaunchTime(instance.getLaunchTime().toString());
                    instances.add(spotleftInfo);

                    savedInstances.forEach( s -> {
                        try{
                            if (s.getName().equals(spotleftInfo.getName())){
                                spotleftInfo.setIsSaved(true);
                                spotleftInfo.setRequireSpot(s.getRequireSpot());
                            }
                        }catch(NullPointerException e){
                            e.printStackTrace();
                        }
                    });

                    // System.out.println(spotleftInfo.getInstanceId());
                }
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
            // System.out.println(response.toString());
            // instances.add(response.getReservations().getInstances());
        }


        model.addAttribute("instances",instances);
        return "instances/current";
    }

}
