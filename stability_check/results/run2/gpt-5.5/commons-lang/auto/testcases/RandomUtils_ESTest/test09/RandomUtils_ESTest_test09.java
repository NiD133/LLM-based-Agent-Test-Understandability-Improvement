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

    private static final long NEGATIVE_START_INCLUSIVE = -176L;
    private static final long END_EXCLUSIVE = 819016964741450255L;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        assertNextLongRejectsNegativeStart(NEGATIVE_START_INCLUSIVE, END_EXCLUSIVE);
    }

    private void assertNextLongRejectsNegativeStart(final long startInclusive, final long endExclusive) {
        try {
            RandomUtils.nextLong(startInclusive, endExclusive);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
