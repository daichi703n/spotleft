package com.daichi703n.spotleft.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Tag;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                    spotleftInfo.setAvailabilityZone(instance.getPlacement().getAvailabilityZone());
                    spotleftInfo.setLaunchTime(instance.getLaunchTime().toString());
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

                    log.debug(spotleftInfo.getInstanceId());
                    log.debug(instance.toString());
                }
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
            log.trace(response.toString());
            // instances.add(response.getReservations().getInstances());
        }
        return instances;
    }
}
