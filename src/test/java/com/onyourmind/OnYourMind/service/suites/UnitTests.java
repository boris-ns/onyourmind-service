package com.onyourmind.OnYourMind.service.suites;

import com.onyourmind.OnYourMind.service.PostCommentImplUnitTest;
import com.onyourmind.OnYourMind.service.PostServiceImplUnitTest;
import com.onyourmind.OnYourMind.service.UserServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        UserServiceUnitTest.class,
        PostServiceImplUnitTest.class,
        PostCommentImplUnitTest.class
})
public class UnitTests {
}
