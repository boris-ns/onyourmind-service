package com.onyourmind.OnYourMind.controller.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UnitTests.class,
        IntegrationsTests.class
})
public class AllTests {
}
