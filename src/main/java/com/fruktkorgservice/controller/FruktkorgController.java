package com.fruktkorg.controller;

import com.fruktkorg.common.PersonUtil;
import com.fruktkorg.common.exception.AccessMissingException;
import com.fruktkorg.common.model.Permission;
import com.fruktkorg.common.model.Frukt;
import com.fruktkorg.common.model.Fruktkorg;
import com.fruktkorg.controller.util.JS;
import com.fruktkorg.service.FruktkorgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("fruktkorg")
public class FruktkorgController {
    @Autowired
    FruktkorgService fruktkorgService;

    @GetMapping()
    public List<Fruktkorg> findAll() throws AccessMissingException {
        PersonUtil.validatePermissionForLoggedInUser(Permission.SEARCH);
        return FruktkorgService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) throws AccessMissingException {
        PersonUtil.validatePermissionForLoggedInUser(Permission.SEARCH);
        Optional<Fruktkorg> fruktkorg = fruktkorgService.findById(id);
       if (fruktkorg.isPresent()) {
           return JS.message(HttpStatus.OK, fruktkorg.get() );
       } else {
           return JS.message(HttpStatus.NOT_FOUND, "Unable to find Fruktkorg with id " + id);
       }

    }

    @GetMapping("/frukt/{type}")
    public List<Fruktkorg> findByFrukt(@PathVariable String type) throws AccessMissingException {
        PersonUtil.validatePermissionForLoggedInUser(Permission.SEARCH);
        return fruktkorgService.findByFrukt(type);
    }
}
