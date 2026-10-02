package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test10 extends Validate_ESTest_scaffolding {

    private static final boolean INVALID_FALSE_CONDITION = true;
    private static final String VALIDATION_MESSAGE = "(iS;>>ZVW";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        try {
            Validate.isFalse(INVALID_FALSE_CONDITION, VALIDATION_MESSAGE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
