package com.cmpe275.OpenHome.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAnnotation
public class WelcomeController {
    private static Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> map = new HashMap<>();
        map.put("Test", "API 1.0 version is live");
        return map;
    }

    @RequestMapping("/")
    public String index(Model model) {
        logger.debug("Welcome to Open Home ...");
        logger.info("Welcome to open home");
        model.addAttribute("msg", "Welcome to Air Bnb of SJSU");
        model.addAttribute("today", new Date());
        return "index";
    }
}
