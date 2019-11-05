package com.daichi703n.spotleft.web;

import java.security.Principal;
import java.util.Objects;

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
    // TODO: move to Interceptor
    public String index(Model model, Principal principal) {
        log.info("Request received.");
        String readmeUrl = "https://github.com/daichi703n/spotleft/blob/master/README.md";
        RestTemplate restTemplate = new RestTemplate();
        String resultStr = restTemplate.getForObject(readmeUrl, String.class);

        String findStart = "<article ";
        String findEnd = "</article>";
        String readmeStr = resultStr
            .substring(resultStr.indexOf(findStart), resultStr.indexOf(findEnd)+findEnd.length())
            .replace("/daichi703n/spotleft","https://github.com/daichi703n/spotleft");

        // TODO: move to Interceptor
        if (Objects.nonNull(principal)) {
            System.out.println(principal.toString());
            String notify_message = principal.getName();
            model.addAttribute("notify_message",notify_message);
        }
        model.addAttribute("msg",readmeStr);
        return "index";
    }

}
