package com.daichi703n.spotleft.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.Response;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Tag;
import com.daichi703n.spotleft.domain.*;

@Service
public class AwsInstanceService {

    @Autowired
    private SavedInstanceService savedInstanceService;

    public List<SpotleftInfo> findAll() {

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        boolean done = false;

        List<SpotleftInfo> instances = new ArrayList<SpotleftInfo>();
        List<SavedInstance> savedInstances = savedInstanceService.findAll();

        DescribeInstancesRequest request = new DescribeInstancesRequest();
        while(!done) {
            DescribeInstancesResult response = ec2.describeInstances(request);

            for(Reservation reservation : response.getReservations()) {
                for(Instance instance : reservation.getInstances()) {
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
                    if(instance.getInstanceLifecycle() == null){
                        spotleftInfo.setLifecycle("normal");
                    }else{
                        spotleftInfo.setLifecycle(instance.getInstanceLifecycle());
                    }
                    spotleftInfo.setState(instance.getState().getName());
                    spotleftInfo.setLaunchTime(instance.getLaunchTime().toString());
                    if (System.getenv("SPOTLEFT_EXCLUDE_NAME") != null && spotleftInfo.getName().contains(System.getenv("SPOTLEFT_EXCLUDE_NAME"))){
                        continue;
                    }
                    instances.add(spotleftInfo);

                    savedInstances.forEach( s -> {
                        try{
                            if (s.getName().equals(spotleftInfo.getName())){
                                spotleftInfo.setIsSaved(true);
                                spotleftInfo.setSavedId(s.getId());
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
        return instances;
    }
}
