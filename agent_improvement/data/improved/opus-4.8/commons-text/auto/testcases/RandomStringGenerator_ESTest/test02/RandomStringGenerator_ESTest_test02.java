package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test02 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomStringGenerator#generate(int, int)} rejects a range whose
     * maximum length is smaller than its minimum length. Here the minimum length is the very
     * large value 1,114,111 (Character.MAX_CODE_POINT) while the maximum length is 0, so the
     * generator must reject the call with an IllegalArgumentException raised by Apache Commons
     * Lang's Validate utility.
     */
    @Test(timeout = 4000)
    public void generateRejectsMaxLengthSmallerThanMinLength() throws Throwable {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().get();

        int minLengthInclusive = 1114111;
        int maxLengthInclusive = 0;

        try {
            generator.generate(minLengthInclusive, maxLengthInclusive);
            fail("Expected IllegalArgumentException because maximum length is smaller than minimum length");
        } catch (IllegalArgumentException expected) {
            // Message: "Maximum length 0 is smaller than minimum length 1114111."
            verifyException("org.apache.commons.lang3.Validate", expected);
        }
    }
}
