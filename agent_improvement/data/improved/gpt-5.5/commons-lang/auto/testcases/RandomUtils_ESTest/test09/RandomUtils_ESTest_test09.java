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
public class RandomUtils_ESTest_test09 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void nextLongRejectsNegativeStartValue() throws Throwable {
        final long negativeStartInclusive = -176L;
        final long endExclusive = 819016964741450255L;

        try {
            RandomUtils.nextLong(negativeStartInclusive, endExclusive);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            verifyException("org.apache.commons.lang3.Validate", expected);
        }
    }
}
