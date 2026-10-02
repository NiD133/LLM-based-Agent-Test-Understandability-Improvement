package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test05 extends Validate_ESTest_scaffolding {

    private static final String EXPECTED_EXCEPTION_MESSAGE = "Expecting exception: IllegalArgumentException";
    private static final String VALIDATE_CLASS_UNDER_TEST = "org.jsoup.helper.Validate";

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        try {
            Validate.notEmptyParam((String) null, (String) null);
            fail(EXPECTED_EXCEPTION_MESSAGE);
        } catch (IllegalArgumentException expectedException) {
            verifyException(VALIDATE_CLASS_UNDER_TEST, expectedException);
        }
    }
}
