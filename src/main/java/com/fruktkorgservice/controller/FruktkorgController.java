package com.fruktkorgservice.controller;

import com.common.util.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fruktkorgservice.common.exception.FruktMissingException;
import com.fruktkorgservice.common.exception.FruktkorgMissingException;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.common.model.dto.FruktUpdateDTO;
import com.fruktkorgservice.common.model.dto.FruktkorgCreateDTO;
import com.fruktkorgservice.common.model.dto.FruktkorgUpdateDTO;
import com.fruktkorgservice.service.FruktkorgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/fruktkorg")
public class FruktkorgController {
    @Autowired
    FruktkorgService fruktkorgService;

    @GetMapping()
    public ResponseEntity<?> getFruktkorgar() {
        return JSON.message(HttpStatus.OK, fruktkorgService.getFruktkorgar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFruktkorg(@PathVariable long id) {

        try {
            return JSON.message(HttpStatus.OK, fruktkorgService.getFruktkorg(id));

        } catch (FruktkorgMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find Fruktkorg with id " + id);
        }

    }

    @GetMapping("/frukt/{type}")
    public ResponseEntity<?> findByFrukt(@PathVariable String type) {
        return JSON.message(HttpStatus.OK, fruktkorgService.getFruktkorgarByFruktType(type));
    }

    @PostMapping
    public ResponseEntity<?> createFruktkorg(@RequestBody @Valid FruktkorgCreateDTO fruktkorg) {
        return JSON.message(HttpStatus.OK, fruktkorgService.saveFruktkorg(new Fruktkorg(fruktkorg)));
    }

    @PutMapping("")
    public ResponseEntity<?> updateFruktkorg(@RequestBody @Valid FruktkorgUpdateDTO fruktkorg) {
        try {
            fruktkorgService.getFruktkorg(fruktkorg.getId());
            for (FruktUpdateDTO frukt : fruktkorg.getFruktList()) {
                fruktkorgService.getFrukt(frukt.getId());
            }
            return JSON.message(HttpStatus.OK, fruktkorgService.saveFruktkorg(new Fruktkorg(fruktkorg)));
        } catch (FruktkorgMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find Fruktkorg with id " + e.getFruktkorgId());
        } catch (FruktMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find Frukt with id " + e.getId());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFruktkorg(@PathVariable long id) {
        try {
            fruktkorgService.getFruktkorg(id);
            fruktkorgService.deleteFruktkorg(id);
            return JSON.message(HttpStatus.OK, "Fruktkorg with id " + id + " deleted.");
        } catch (FruktkorgMissingException e) {
            return JSON.message(HttpStatus.NOT_FOUND, "Unable to find Fruktkorg with id " + id);
        }
    }


}
