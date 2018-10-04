package com.fruktkorgservice.service;

import com.fruktkorgservice.common.exception.FruktMissingException;
import com.fruktkorgservice.common.exception.FruktkorgMissingException;
import com.fruktkorgservice.common.model.Frukt;
import com.fruktkorgservice.common.model.FruktRepository;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.common.model.FruktkorgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FruktkorgService {
    @Autowired
    FruktkorgRepository fruktkorgRepository;
    @Autowired
    FruktRepository fruktRepository;

    public List<Fruktkorg> getFruktkorg() {
        return fruktkorgRepository.findAll();
    }

    public Fruktkorg getFruktkorg(long id) throws FruktkorgMissingException {
        return fruktkorgRepository.findFruktkorgByid(id).orElseThrow(() ->
                new FruktkorgMissingException("Unable to find Fruktkorg with id: " + id, id));
    }

    public Frukt getFrukt(long id) throws FruktMissingException {
        return fruktRepository.findFruktByid(id).orElseThrow(() ->
                new FruktMissingException("Unable to find Fruktkorg with id: " + id, id));
    }

    public List<Fruktkorg> getFruktkorgarByFruktType(String type) {
        return fruktkorgRepository.findFruktkorgarByFruktType(type);
    }

    public Fruktkorg saveFruktkorg(Fruktkorg fruktkorg) {
        fruktkorg.setLastChanged(Instant.now());
        return fruktkorgRepository.save(fruktkorg);
    }

    public void deleteFruktkorg(long id) {
        fruktkorgRepository.deleteById(id);
    }

}
