package com.fruktkorgservice;

import com.fruktkorgservice.common.model.Frukt;
import com.fruktkorgservice.common.model.FruktRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class FruktRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FruktRepository fruktRepository;

    @Test
    public void findFruktById() {
        Frukt frukt = new Frukt();
        frukt.setType("Päron");
        frukt.setAmount(5);

        entityManager.persist(frukt);

        Optional<Frukt> optionalFrukt = fruktRepository.findFruktByid(1L);
        Assert.assertTrue(optionalFrukt.isPresent());
        Frukt foundFrukt = optionalFrukt.get();
        Assert.assertEquals("Päron", foundFrukt.getType());
        Assert.assertEquals(5, foundFrukt.getAmount());
        Assert.assertEquals(1, foundFrukt.getId());

    }

}
