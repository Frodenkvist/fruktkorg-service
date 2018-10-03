package com.fruktkorgservice.service;

import com.fruktkorgservice.FruktkorgRepository;
import com.fruktkorgservice.common.exception.FruktkorgMissingException;
import com.fruktkorgservice.common.model.Fruktkorg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FruktkorgService {
    @Autowired
    FruktkorgRepository fruktkorgRepository;

    public List<Fruktkorg> getFruktkorg() {
        return fruktkorgRepository.findAll();
    }

    public Fruktkorg getFruktkorg(long id) throws FruktkorgMissingException {
        return fruktkorgRepository.findFruktkorgByid(id).orElseThrow(() ->
                new FruktkorgMissingException("Unable to find Fruktkorg with id: " + id, id));
    }

    public List<Fruktkorg> getFruktkorgarByFruktType(String type) {
        return fruktkorgRepository.findFruktkorgarByFruktType(type);
    }

}
