package com.onyourmind.OnYourMind.service.suites;

import com.onyourmind.OnYourMind.service.UserServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    UserServiceUnitTest.class
})
public class UnitTests {
}
