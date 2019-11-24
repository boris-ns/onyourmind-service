package com.onyourmind.OnYourMind.repository.suites;

import com.onyourmind.OnYourMind.repository.AuthorityRepositoryIntegrationTest;
import com.onyourmind.OnYourMind.repository.ConfirmationTokenRepositoryIntegrationTest;
import com.onyourmind.OnYourMind.repository.UserRepositoryIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        AuthorityRepositoryIntegrationTest.class,
        ConfirmationTokenRepositoryIntegrationTest.class,
        UserRepositoryIntegrationTest.class
})
public class IntegrationTests {
}
