package com.daichi703n.spotleft.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class SpotleftController {

    @RequestMapping(method = RequestMethod.GET)
    // public String hello() {
    public String hello(Model model) {
        model.addAttribute("msg","サンプルメッセージ！");
        return "index";
        // return "hello/hello";
    }

}
