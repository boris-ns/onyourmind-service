package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorityRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void whenFindByNameReturnAuthority() {
        final String authorityName = "AUTHORITY";
        Authority authority = new Authority();
        authority.setName(authorityName);

        entityManager.persist(authority);
        entityManager.flush();

        Authority foundAuthority = authorityRepository.findByName(authorityName);
        assertEquals(authority.getId(), foundAuthority.getId());
    }

    @Test
    public void whenFindByNameReturnNull() {
        Authority foundAuthority = authorityRepository.findByName("auth");
        assertNull(foundAuthority);
    }
}
