package com.fruktkorgservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fruktkorgservice.common.exception.FruktkorgMissingException;
import com.fruktkorgservice.common.model.Frukt;
import com.fruktkorgservice.common.model.Fruktkorg;
import com.fruktkorgservice.common.model.dto.FruktCreateDTO;
import com.fruktkorgservice.common.model.dto.FruktUpdateDTO;
import com.fruktkorgservice.common.model.dto.FruktkorgCreateDTO;
import com.fruktkorgservice.common.model.dto.FruktkorgUpdateDTO;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Assert.assertEquals(fruktkorgar, returnFruktkorgar);

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

    @Test
    public void getMissingFruktkorg() throws Exception {
        when(fruktkorgService.getFruktkorg(1l)).thenThrow(FruktkorgMissingException.class);
        mvc.perform(get("/v1/fruktkorg/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void findByFrukt() throws Exception {
        List<Fruktkorg> fruktkorgar = createExampleFruktkorgar();
        when(fruktkorgService.getFruktkorgarByFruktType(PEAR_NAME)).thenReturn(fruktkorgar);

        MvcResult result = mvc.perform(get("/v1/fruktkorg/frukt/" + PEAR_NAME)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Fruktkorg> returnFruktkorgar = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Fruktkorg>>() {
                });
        Assert.assertNotNull(returnFruktkorgar);
        Assert.assertEquals(fruktkorgar, returnFruktkorgar);

    }

    @Test
    public void noFruktFound() throws Exception {
        when(fruktkorgService.getFruktkorgarByFruktType(PEAR_NAME)).thenReturn(new ArrayList<>());

        MvcResult result = mvc.perform(get("/v1/fruktkorg/frukt/" + PEAR_NAME)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<Fruktkorg> returnFruktkorgar = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Fruktkorg>>() {
                });
        Assert.assertNotNull(returnFruktkorgar);
        Assert.assertEquals(new ArrayList<>(), returnFruktkorgar);

    }

    @Test
    public void createFruktkorg() throws Exception {
        FruktkorgCreateDTO fruktkorgDTO = new FruktkorgCreateDTO();
        fruktkorgDTO.setName(FRUKTKORG_NAME);
        fruktkorgDTO.setLastChanged(NOW);

        FruktCreateDTO fruktDTO = new FruktCreateDTO();
        fruktDTO.setType(PEAR_NAME);
        fruktDTO.setAmount(PEAR_AMOUNT);

        fruktkorgDTO.setFruktList(Collections.singletonList(fruktDTO));

        Fruktkorg persistedFruktkorg = new Fruktkorg();
        persistedFruktkorg.setName(FRUKTKORG_NAME);
        persistedFruktkorg.setId(1);
        persistedFruktkorg.setLastChanged(NOW);

        Frukt persistedFrukt = new Frukt();
        persistedFrukt.setId(1);
        persistedFrukt.setType(PEAR_NAME);
        persistedFrukt.setAmount(PEAR_AMOUNT);
        persistedFrukt.setFruktkorg(persistedFruktkorg);

        persistedFruktkorg.setFruktList(Collections.singletonList(persistedFrukt));

        when(fruktkorgService.saveFruktkorg(any())).thenReturn(persistedFruktkorg);

        MvcResult result = mvc.perform(post("/v1/fruktkorg")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fruktkorgDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Fruktkorg returnFruktKorg = objectMapper.readValue(result.getResponse().getContentAsString(),
                Fruktkorg.class);

        Assert.assertNotNull(returnFruktKorg);
        Assert.assertEquals(persistedFruktkorg, returnFruktKorg);

    }

    @Test
    public void updateFruktkorg() throws Exception {
        FruktkorgUpdateDTO fruktkorgDTO = new FruktkorgUpdateDTO();
        fruktkorgDTO.setId(1L);
        fruktkorgDTO.setName(FRUKTKORG_NAME);
        fruktkorgDTO.setLastChanged(NOW);

        FruktUpdateDTO pearDTO = new FruktUpdateDTO();
        pearDTO.setId(1l);
        pearDTO.setType(PEAR_NAME);
        pearDTO.setAmount(PEAR_AMOUNT);

        fruktkorgDTO.setFruktList(Arrays.asList(pearDTO));

        Fruktkorg persistedFruktkorg = new Fruktkorg();
        persistedFruktkorg.setName(FRUKTKORG_NAME);
        persistedFruktkorg.setId(1);
        persistedFruktkorg.setLastChanged(NOW);

        Frukt persistedFrukt = new Frukt();
        persistedFrukt.setId(1);
        persistedFrukt.setType(PEAR_NAME);
        persistedFrukt.setAmount(PEAR_AMOUNT);
        persistedFrukt.setFruktkorg(persistedFruktkorg);

        persistedFruktkorg.setFruktList(Collections.singletonList(persistedFrukt));

        when(fruktkorgService.saveFruktkorg(new Fruktkorg(fruktkorgDTO))).thenReturn(persistedFruktkorg);

        MvcResult result = mvc.perform(put("/v1/fruktkorg")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fruktkorgDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Fruktkorg returnFruktKorg = objectMapper.readValue(result.getResponse().getContentAsString(),
                Fruktkorg.class);

        Assert.assertNotNull(returnFruktKorg);
        Assert.assertEquals(persistedFruktkorg, returnFruktKorg);

    }

    @Test
    public void deleteFruktkorg() throws Exception {
        mvc.perform(delete("/v1/fruktkorg/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMissingFruktkorg() throws Exception {
        doThrow(FruktkorgMissingException.class).when(fruktkorgService).deleteFruktkorg(1l);
        mvc.perform(delete("/v1/fruktkorg/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    private List<Fruktkorg> createExampleFruktkorgar() {
        int fruktkorgId = 1;
        int fruktId = 1;
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
