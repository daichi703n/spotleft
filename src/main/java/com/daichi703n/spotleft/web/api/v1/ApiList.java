package com.daichi703n.spotleft.web.api.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiList {

    @RequestMapping(method = RequestMethod.GET)
    public String[] apiList() {
        String[] apiList = {
            "v1/instances"
            ,"v1/instances/illegal"
            ,"v1/saved"
        };
        return apiList;
    }

}
