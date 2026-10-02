package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test15 extends Validate_ESTest_scaffolding {

    /**
     * Validates that isTrue(true, msg) does not throw when the boolean condition is satisfied.
     * The error message is only used in the exception if the condition were false.
     */
    @Test(timeout = 4000)
    public void test_isTrue_withTrueConditionAndCustomMessage_doesNotThrow() throws Throwable {
        Validate.isTrue(true, "W\"N+(;C");
    }
}
