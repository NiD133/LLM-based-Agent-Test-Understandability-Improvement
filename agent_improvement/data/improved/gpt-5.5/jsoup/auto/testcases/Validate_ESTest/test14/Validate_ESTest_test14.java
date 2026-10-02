package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test14 extends Validate_ESTest_scaffolding {

    private static final boolean INVALID_CONDITION = false;
    private static final String EXPECTED_EXCEPTION_MESSAGE = "W\"N+(;C";

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        try {
            Validate.isTrue(INVALID_CONDITION, EXPECTED_EXCEPTION_MESSAGE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expectedException) {
            verifyException("org.jsoup.helper.Validate", expectedException);
        }
    }
}
