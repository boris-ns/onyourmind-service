package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.constants.ConfirmationTokenConstants;
import com.onyourmind.OnYourMind.model.ConfirmationToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConfirmationTokenRepositoryIntegrationTest {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void whenFindByTokenReturnNull() {
        ConfirmationToken token = confirmationTokenRepository.findByToken(ConfirmationTokenConstants.DB_NON_EXISTING);
        assertNull(token);
    }
}
