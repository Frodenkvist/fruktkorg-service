package com.fruktkorgservice.controller;

import com.fruktkorgservice.service.FruktkorgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fruktkorg")
public class FruktkorgController {
    @Autowired
    private FruktkorgService fruktkorgService;
}
