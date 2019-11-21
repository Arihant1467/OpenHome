package com.cmpe275.OpenHome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WelcomeController {
    private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @GetMapping("/rest/ping")
    @ResponseBody
    public Map<String, Integer> ping() {
        Map<String, Integer> map = new HashMap<>();
        map.put("health", 200);
        return map;
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.debug("Welcome to Open Home ...");
        logger.info("Welcome to open home");
        model.addAttribute("msg", "Welcome to Air Bnb of SJSU");
        model.addAttribute("today", new Date());
        return "index";
    }
}
