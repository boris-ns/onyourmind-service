package com.onyourmind.OnYourMind.repository.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        IntegrationTests.class,
        UnitTests.class
})
public class AllTests {
}
