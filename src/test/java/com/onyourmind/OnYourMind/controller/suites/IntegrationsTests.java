package com.onyourmind.OnYourMind.controller.suites;

import com.onyourmind.OnYourMind.controller.AuthenticationControllerIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthenticationControllerIntegrationTest.class
})
public class IntegrationsTests {
}
