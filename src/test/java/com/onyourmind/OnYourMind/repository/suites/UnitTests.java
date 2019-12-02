package com.onyourmind.OnYourMind.repository.suites;

import com.onyourmind.OnYourMind.repository.AuthorityRepositoryUnitTest;
import com.onyourmind.OnYourMind.repository.ConfirmationTokenRepositoryUnitTest;
import com.onyourmind.OnYourMind.repository.PostRepositoryUnitTest;
import com.onyourmind.OnYourMind.repository.UserRepositoryUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        AuthorityRepositoryUnitTest.class,
        ConfirmationTokenRepositoryUnitTest.class,
        UserRepositoryUnitTest.class,
        PostRepositoryUnitTest.class
})
public class UnitTests {
}
