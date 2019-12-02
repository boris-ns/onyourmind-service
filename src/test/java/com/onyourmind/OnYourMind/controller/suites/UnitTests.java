package com.onyourmind.OnYourMind.controller.suites;

import com.onyourmind.OnYourMind.controller.AuthenticationControllerUnitTest;
import com.onyourmind.OnYourMind.controller.PostControllerUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PostControllerUnitTest.class,
        AuthenticationControllerUnitTest.class
})
public class UnitTests {
}
