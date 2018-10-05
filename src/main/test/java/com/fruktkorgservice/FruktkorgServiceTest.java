package com.fruktkorgservice;

import com.fruktkorgservice.common.exception.FruktMissingException;
import com.fruktkorgservice.common.exception.FruktkorgMissingException;
import com.fruktkorgservice.common.model.Frukt;
import com.fruktkorgservice.common.model.FruktRepository;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.common.model.FruktkorgRepository;
import com.fruktkorgservice.service.FruktkorgService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FruktkorgServiceTest {

    @TestConfiguration
    static class FruktkorgServiceTestContextConfiguration {
        @Bean
        public FruktkorgService fruktkorgService() {
            return new FruktkorgService();
        }
    }

    @Autowired
    FruktkorgService fruktkorgService;

    @MockBean
    FruktkorgRepository fruktkorgRepository;

    @MockBean
    FruktRepository fruktRepository;

    public static final String FRUKTKORG_NAME = "Korgen";
    public static final String PEAR_NAME = "Päron";
    public static final String APPLE_NAME = "Äpple";
    public static final Instant NOW = Instant.now();
    public static final int PEAR_AMOUNT = 5;
    public static final int APPLE_AMOUNT = 10;

    @Test
    public void getFruktkorgar() {
        List<Fruktkorg> fruktkorgar = createExampleFruktkorgar();
        when(fruktkorgRepository.findAll()).thenReturn(fruktkorgar);

        List<Fruktkorg> fruktkorgarPersisted = fruktkorgService.getFruktkorgar();
        Assert.assertEquals(fruktkorgar, fruktkorgarPersisted);
    }

    @Test
    public void getFruktkorg() throws FruktkorgMissingException {
        Fruktkorg fruktkorg = createExampleFruktkorg();
        when(fruktkorgRepository.findFruktkorgByid(1L)).thenReturn(Optional.of(fruktkorg));

        Fruktkorg persistedFruktkorg = fruktkorgService.getFruktkorg(1L);
        Assert.assertEquals(fruktkorg, persistedFruktkorg);
    }

    @Test(expected = FruktkorgMissingException.class)
    public void getMissingFruktkorg() throws FruktkorgMissingException {
        when(fruktkorgRepository.findFruktkorgByid(1l)).thenReturn(Optional.empty());
        fruktkorgService.getFruktkorg(1l);
    }

    @Test
    public void getFrukt() throws FruktMissingException {
        Frukt pear = createExampleFrukt();
        when(fruktRepository.findFruktByid(1l)).thenReturn(Optional.of(pear));
        Frukt persistedPear = fruktkorgService.getFrukt(1l);
        Assert.assertEquals(pear, persistedPear);
    }

    @Test(expected = FruktMissingException.class)
    public void getMissingFrukt() throws FruktMissingException {
        when(fruktRepository.findFruktByid(1l)).thenReturn(Optional.empty());
        fruktkorgService.getFrukt(1l);
    }

    @Test
    public void getFruktkorgarByFruktType() {
        List<Fruktkorg> fruktkorgar = createExampleFruktkorgar();
        when(fruktkorgRepository.findFruktkorgarByFruktType(PEAR_NAME)).thenReturn(fruktkorgar);
        List<Fruktkorg> fruktkorgarByFruktType = fruktkorgService.getFruktkorgarByFruktType(PEAR_NAME);
        Assert.assertEquals(fruktkorgar, fruktkorgarByFruktType);
    }

    @Test
    public void getEmtpyListOfFruktkorgarByFruktType() {
        when(fruktkorgRepository.findFruktkorgarByFruktType(PEAR_NAME)).thenReturn(new ArrayList<>());
        List<Fruktkorg> fruktkorgarByFruktType = fruktkorgService.getFruktkorgarByFruktType(PEAR_NAME);
        Assert.assertNotNull(fruktkorgarByFruktType);
        int fruktkorgarSize = 0;
        Assert.assertEquals(fruktkorgarSize, fruktkorgarByFruktType.size());
    }

    @Test
    public void createFruktkorg() {
        Fruktkorg persistedFruktkorg = createExampleFruktkorg();
        Fruktkorg toBePersistedFruktkorg = createExampleFruktkorg();

        int defaultValue = 0;
        toBePersistedFruktkorg.setId(defaultValue);
        toBePersistedFruktkorg.getFruktList().get(0).setId(defaultValue);
        toBePersistedFruktkorg.getFruktList().get(1).setId(defaultValue);

        when(fruktkorgRepository.save(toBePersistedFruktkorg)).thenReturn(persistedFruktkorg);
        Fruktkorg savedFruktkorg = fruktkorgService.saveFruktkorg(toBePersistedFruktkorg);
        Assert.assertEquals(persistedFruktkorg, savedFruktkorg);
    }

    @Test
    public void updateFruktkorg() {
        Fruktkorg fruktkorg = createExampleFruktkorg();
        when(fruktkorgRepository.save(fruktkorg)).thenReturn(fruktkorg);
        Fruktkorg savedFruktkorg = fruktkorgService.saveFruktkorg(fruktkorg);
        Assert.assertEquals(fruktkorg, savedFruktkorg);
    }

    @Test
    public void deleteFruktkorg() throws FruktkorgMissingException {
        when(fruktkorgRepository.deleteFruktkorgById(1l)).thenReturn(1l);
        fruktkorgService.deleteFruktkorg(1l);
    }

    @Test(expected = FruktkorgMissingException.class)
    public void deleteMissingFruktkorg() throws FruktkorgMissingException {
        when(fruktkorgRepository.deleteFruktkorgById(1l)).thenReturn(0L);
        fruktkorgService.deleteFruktkorg(1l);
    }

    private Frukt createExampleFrukt() {
        Frukt pear = new Frukt();
        pear.setId(1);
        pear.setType(PEAR_NAME);
        pear.setAmount(PEAR_AMOUNT);
        return pear;
    }

    private Fruktkorg createExampleFruktkorg() {
        Fruktkorg fruktkorg = new Fruktkorg();
        fruktkorg.setId(1);
        fruktkorg.setName(FRUKTKORG_NAME);
        fruktkorg.setLastChanged(NOW);

        Frukt pear = new Frukt();
        pear.setId(1);
        pear.setFruktkorg(fruktkorg);
        pear.setType(PEAR_NAME);
        pear.setAmount(PEAR_AMOUNT);

        Frukt apple = new Frukt();
        apple.setId(2);
        apple.setFruktkorg(fruktkorg);
        apple.setType(APPLE_NAME);
        apple.setAmount(APPLE_AMOUNT);

        fruktkorg.setFruktList(Arrays.asList(pear, apple));
        return fruktkorg;
    }

    private List<Fruktkorg> createExampleFruktkorgar() {
        int fruktkorgId = 0;
        int fruktId = 0;
        List<Fruktkorg> fruktkorgar = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Fruktkorg fruktkorg = new Fruktkorg();
            fruktkorg.setId(fruktkorgId++);
            fruktkorg.setName(FRUKTKORG_NAME);
            fruktkorg.setLastChanged(NOW);

            Frukt pear = new Frukt();
            pear.setId(fruktId++);
            pear.setFruktkorg(fruktkorg);
            pear.setType(PEAR_NAME);
            pear.setAmount(PEAR_AMOUNT);

            Frukt apple = new Frukt();
            apple.setId(fruktId++);
            apple.setFruktkorg(fruktkorg);
            apple.setType(APPLE_NAME);
            apple.setAmount(APPLE_AMOUNT);

            fruktkorg.setFruktList(Arrays.asList(pear, apple));
            fruktkorgar.add(fruktkorg);
        }

        return fruktkorgar;
    }

}
