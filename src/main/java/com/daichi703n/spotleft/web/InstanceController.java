package com.daichi703n.spotleft.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daichi703n.spotleft.domain.AwsInstanceService;
import com.daichi703n.spotleft.domain.SavedInstance;
import com.daichi703n.spotleft.domain.SavedInstanceService;
import com.daichi703n.spotleft.domain.SpotleftInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/instances")
public class InstanceController {

    @Autowired
    private AwsInstanceService awsInstanceService;

    @RequestMapping(method = RequestMethod.GET)
    public String instances(Model model) {

        List<SpotleftInfo> instances = awsInstanceService.findAll();

        model.addAttribute("instances",instances);
        return "instances/current";
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
