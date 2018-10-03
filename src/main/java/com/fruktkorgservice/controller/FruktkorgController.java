package com.fruktkorgservice.controller;

import com.fruktkorgservice.common.exception.FruktkorgMissingException;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.controller.util.JS;
import com.fruktkorgservice.service.FruktkorgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("fruktkorg")
public class FruktkorgController {
    @Autowired
    FruktkorgService fruktkorgService;

    @GetMapping()
    public List<Fruktkorg> findAll()  {
        return fruktkorgService.getFruktkorg();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id)  {

        try {
            return JS.message(HttpStatus.OK, fruktkorgService.getFruktkorg(id) );

        } catch (FruktkorgMissingException e){
            return JS.message(HttpStatus.NOT_FOUND, "Unable to find Fruktkorg with id " + id);
        }

    }

    @GetMapping("/frukt/{type}")
    public List<Fruktkorg> findByFrukt(@PathVariable String type) {
        return fruktkorgService.getFruktkorgarByFruktType(type);
    }
}
