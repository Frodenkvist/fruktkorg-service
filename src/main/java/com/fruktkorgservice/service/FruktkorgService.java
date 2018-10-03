package com.fruktkorg.service;

import com.fruktkorg.common.model.Frukt;
import com.fruktkorg.common.model.Fruktkorg;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FruktkorgService {
    public static List<Fruktkorg> getAll() {
        List<Fruktkorg> fruktkorgar = new ArrayList<>();

        int fruktkorgId = 1;
        int fruktId = 1;

        for (int i = 0; i < 100; i++) {
            Fruktkorg fruktkorg = new Fruktkorg();
            fruktkorg.setId(fruktkorgId++);
            fruktkorg.setName("Hallen " + fruktkorgId);
            fruktkorg.setLastChanged("4 September");

            Frukt banana = new Frukt("Banan", getRandomNumber(), fruktkorg);
            banana.setId(fruktId++);

            Frukt apple = new Frukt("Äpple", getRandomNumber(), fruktkorg);
            apple.setId(fruktId++);

            fruktkorg.setFruktList(Arrays.asList(banana, apple));
            fruktkorgar.add(fruktkorg);

            Fruktkorg fruktkorg2 = new Fruktkorg();
            fruktkorg2.setId(fruktkorgId++);
            fruktkorg2.setName("Köket " + fruktkorgId);
            fruktkorg2.setLastChanged("5 September");

            Frukt orange = new Frukt("Apelsin", getRandomNumber(), fruktkorg2);
            orange.setId(fruktId++);

            Frukt lemon = new Frukt("Citron", getRandomNumber(), fruktkorg2);
            lemon.setId(fruktId++);

            fruktkorg2.setFruktList(Arrays.asList(orange, lemon));
            fruktkorgar.add(fruktkorg2);

            Fruktkorg fruktkorg3 = new Fruktkorg();
            fruktkorg3.setId(fruktkorgId++);
            fruktkorg3.setName("Vardagsrummet " + fruktkorgId);
            fruktkorg3.setLastChanged("6 September");

            Frukt apple2 = new Frukt("Äpple", getRandomNumber(), fruktkorg3);
            apple2.setId(fruktId++);

            fruktkorg3.setFruktList(Arrays.asList(apple2));
            fruktkorgar.add(fruktkorg3);

            Fruktkorg fruktkorg4 = new Fruktkorg();
            fruktkorg4.setId(fruktkorgId++);
            fruktkorg4.setName("Kafferummet " + fruktkorgId);
            fruktkorg4.setLastChanged("1 September");

            Frukt orange2 = new Frukt("Apelsin", getRandomNumber(), fruktkorg4);
            orange2.setId(fruktId++);

            Frukt lemon2 = new Frukt("Citron", getRandomNumber(), fruktkorg4);
            lemon2.setId(fruktId++);

            Frukt watermelon = new Frukt("Vattenmelon", 1, fruktkorg4);
            watermelon.setId(fruktId++);

            fruktkorg4.setFruktList(Arrays.asList(orange2, lemon2, watermelon));
            fruktkorgar.add(fruktkorg4);

            Fruktkorg fruktkorg5 = new Fruktkorg();
            fruktkorg5.setId(fruktkorgId++);
            fruktkorg5.setName("Kafferummet " + fruktkorgId);
            fruktkorg5.setLastChanged("1 September");

            Frukt apple3 = new Frukt("Äpple", getRandomNumber(), fruktkorg5);
            apple3.setId(fruktId++);

            Frukt pear = new Frukt("Päron", getRandomNumber(), fruktkorg5);
            pear.setId(fruktId++);

            fruktkorg5.setFruktList(Arrays.asList(apple3, pear));
            fruktkorgar.add(fruktkorg5);
        }

        return fruktkorgar;
    }

    private static int getRandomNumber() {
        return new Random().nextInt(10 - 1 +1) + 1;
    }

    public Optional<Fruktkorg> findById(long id) {
        return getAll().stream()
                .filter(fruktkorg -> fruktkorg.getId() == id)
                .findFirst();
    }

    public List<Fruktkorg> findByFrukt(String fruktType) {
        List<Fruktkorg> fruktkorgar = getAll().stream()
                .filter(fruktkorg -> fruktkorg.getFruktList().stream().anyMatch(frukt -> frukt.getType().toLowerCase().contains(fruktType.toLowerCase())))
                .collect(Collectors.toList());
        return fruktkorgar;
    }

}
