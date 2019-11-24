package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.constants.AuthorityConstants;
import com.onyourmind.OnYourMind.model.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorityRepositoryIntegrationTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void whenFindByNameReturnAuthority() {
        Authority found = authorityRepository.findByName(AuthorityConstants.DB_ROLE_ADMIN);
        assertEquals(AuthorityConstants.DB_ROLE_ADMIN, found.getName());
    }

    @Test
    public void whenFindByNameReturnNull() {
        Authority found = authorityRepository.findByName(AuthorityConstants.DB_ROLE_NON_EXISTING);
        assertNull(found);
    }
}
