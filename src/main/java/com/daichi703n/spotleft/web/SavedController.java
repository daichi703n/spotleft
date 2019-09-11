package com.daichi703n.spotleft.web;

import java.util.ArrayList;
import java.util.List;

import com.daichi703n.spotleft.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ComponentScan
@Controller
@RequestMapping("/saved")
public class SavedController {

    @Autowired
    private SavedInstancesService service;

    @RequestMapping(method = RequestMethod.GET)
    public String instances(Model model) {

        List<SavedInstances> instances = new ArrayList<SavedInstances>();

        instances = service.findAll();

        model.addAttribute("savedInstances",instances);
        return "instances/saved";
    }

}
