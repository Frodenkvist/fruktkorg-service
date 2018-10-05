package com.fruktkorgservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fruktkorgservice.common.model.Frukt;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.controller.FruktkorgController;
import com.fruktkorgservice.service.FruktkorgService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FruktkorgController.class)
@ActiveProfiles("test")
public class FruktkorgControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    FruktkorgService fruktkorgService;

    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static final String FRUKTKORG_NAME = "Korgen";
    public static final String PEAR_NAME = "Päron";
    public static final String APPLE_NAME = "Äpple";
    public static final Instant NOW = Instant.now();
    public static final int PEAR_AMOUNT = 5;
    public static final int APPLE_AMOUNT = 10;

    @Test
    public void getFruktkorgar() throws Exception {
        List<Fruktkorg> fruktkorgar = createExampleFruktkorgar();
        when(fruktkorgService.getFruktkorgar()).thenReturn(fruktkorgar);

        MvcResult result = mvc.perform(get("/v1/fruktkorg")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Fruktkorg> returnFruktkorgar = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Fruktkorg>>() {
                });
        Assert.assertNotNull(returnFruktkorgar);

    }

    @Test
    public void getFruktkorg() throws Exception {
        Fruktkorg fruktkorg = createExampleFruktkorg();
        when(fruktkorgService.getFruktkorg(fruktkorg.getId())).thenReturn(fruktkorg);

        MvcResult result = mvc.perform(get("/v1/fruktkorg/" + fruktkorg.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Fruktkorg returnFruktKorg = objectMapper.readValue(result.getResponse().getContentAsString(),
                Fruktkorg.class);
        Assert.assertNotNull(returnFruktKorg);
        Assert.assertEquals(fruktkorg, returnFruktKorg);

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

}
