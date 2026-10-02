package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test11 extends Validate_ESTest_scaffolding {

    private static final String CUSTOM_VALIDATION_MESSAGE = "(ir;>>Z<W";

    // isFalse should not throw when the given value is already false
    @Test(timeout = 4000)
    public void isFalse_withFalseValue_doesNotThrow() throws Throwable {
        Validate.isFalse(false, CUSTOM_VALIDATION_MESSAGE);
    }
}
