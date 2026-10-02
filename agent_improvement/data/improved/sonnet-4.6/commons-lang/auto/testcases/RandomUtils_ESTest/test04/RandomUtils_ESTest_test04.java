package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.security.SecureRandom;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test04 extends RandomUtils_ESTest_scaffolding {

    private static final float START_VALUE = 0F;
    private static final float END_VALUE_LESS_THAN_START = -614.1698F;

    @Test(timeout = 4000)
    public void test04_nextFloat_throwsWhenEndIsLessThanStart() throws Throwable {
        // nextFloat requires startInclusive <= endExclusive; passing a negative end value violates this
        try {
            RandomUtils.nextFloat(START_VALUE, END_VALUE_LESS_THAN_START);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
