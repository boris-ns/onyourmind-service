package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.ConfirmationToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConfirmationTokenRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void whenFindByTokenReturnConfirmationToken() {
        final String token = "123-asdf-123-asdf-4567";
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedDatetime(new Date());

        entityManager.persist(confirmationToken);
        entityManager.flush();

        ConfirmationToken foundToken = confirmationTokenRepository.findByToken(token);
        assertEquals(confirmationToken.getId(), foundToken.getId());
    }
}
