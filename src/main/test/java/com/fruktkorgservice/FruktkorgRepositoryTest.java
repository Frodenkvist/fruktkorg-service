package com.fruktkorgservice;

import com.fruktkorgservice.common.model.Frukt;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.common.model.FruktkorgRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class FruktkorgRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    FruktkorgRepository fruktkorgRepository;
    public static final String FRUKTKORG_NAME = "Korgen";
    public static final String PEAR_NAME = "Päron";
    public static final String APPLE_NAME = "Äpple";
    public static final Instant NOW = Instant.now();
    public static final int PEAR_AMOUNT = 5;
    public static final int APPLE_AMOUNT = 10;

    @Test
    public void findFruktkorgById() {
        Fruktkorg fruktkorg = createFruktkorg();
        entityManager.persist(fruktkorg);

        Optional<Fruktkorg> optionalFruktkorg = fruktkorgRepository.findFruktkorgByid(1L);
        Assert.assertTrue(optionalFruktkorg.isPresent());

        Fruktkorg persistedFruktkorg = optionalFruktkorg.get();

        int fruktkorgId = 1;
        Assert.assertEquals(fruktkorgId, persistedFruktkorg.getId());
        Assert.assertEquals(FRUKTKORG_NAME, persistedFruktkorg.getName());
        Assert.assertEquals(NOW, fruktkorg.getLastChanged());

        int listSize = 2;
        List<Frukt> fruktList = persistedFruktkorg.getFruktList();
        Assert.assertEquals(listSize, fruktList.size());

        Frukt persistedPear = fruktList.get(0);
        int pearID = 1;
        Assert.assertEquals(pearID, persistedPear.getId());
        Assert.assertEquals(PEAR_NAME, persistedPear.getType());
        Assert.assertEquals(PEAR_AMOUNT, persistedPear.getAmount());
        Assert.assertEquals(persistedFruktkorg, persistedPear.getFruktkorg());

        Frukt persistedApple = fruktList.get(1);
        int appleID = 2;
        Assert.assertEquals(appleID, persistedApple.getId());
        Assert.assertEquals(APPLE_NAME, persistedApple.getType());
        Assert.assertEquals(APPLE_AMOUNT, persistedApple.getAmount());
        Assert.assertEquals(persistedFruktkorg, persistedApple.getFruktkorg());
    }

    @Test
    public void findFruktkorgarByFruktType() {
        Fruktkorg fruktkorg = createFruktkorg();
        entityManager.persist(fruktkorg);

        List<Fruktkorg> fruktkorgar = fruktkorgRepository.findFruktkorgarByFruktType(PEAR_NAME);
        int listSize = 1;
        Assert.assertEquals(listSize, fruktkorgar.size());

        Fruktkorg persistedFruktkorg = fruktkorgar.get(0);
        Assert.assertEquals(FRUKTKORG_NAME, persistedFruktkorg.getName());
        Frukt persistedPear = persistedFruktkorg.getFruktList().get(0);
        Assert.assertEquals(PEAR_NAME, persistedPear.getType());
    }


    private Fruktkorg createFruktkorg() {
        Fruktkorg fruktkorg = new Fruktkorg();
        fruktkorg.setName(FRUKTKORG_NAME);
        fruktkorg.setLastChanged(NOW);

        Frukt pear = new Frukt();
        pear.setFruktkorg(fruktkorg);
        pear.setType(PEAR_NAME);
        pear.setAmount(PEAR_AMOUNT);

        Frukt apple = new Frukt();
        apple.setFruktkorg(fruktkorg);
        apple.setType(APPLE_NAME);
        apple.setAmount(APPLE_AMOUNT);

        fruktkorg.setFruktList(Arrays.asList(pear, apple));
        return fruktkorg;
    }
}
