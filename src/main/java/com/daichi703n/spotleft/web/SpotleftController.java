package com.daichi703n.spotleft.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class SpotleftController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        log.info("Request received.");
        String readmeUrl = "https://github.com/daichi703n/spotleft/blob/master/README.md";
        RestTemplate restTemplate = new RestTemplate();
        String resultStr = restTemplate.getForObject(readmeUrl, String.class);

        String findStart = "<article ";
        String findEnd = "</article>";
        String readmeStr = resultStr
            .substring(resultStr.indexOf(findStart), resultStr.indexOf(findEnd)+findEnd.length())
            .replace("/daichi703n/spotleft","https://github.com/daichi703n/spotleft");

        model.addAttribute("msg",readmeStr);
        return "index";
    }

}
