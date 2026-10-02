package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test09 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomStringGenerator.Builder#withinRange(int, int)} rejects a
     * negative minimum code point. The builder delegates the validation to Apache Commons
     * Lang's {@code Validate}, which throws an {@link IllegalArgumentException} stating that
     * the minimum code point is negative.
     */
    @Test(timeout = 4000)
    public void withinRangeWithNegativeMinimumCodePointThrowsIllegalArgumentException() throws Throwable {
        final int negativeCodePoint = -3302;
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        try {
            builder.withinRange(negativeCodePoint, negativeCodePoint);
            fail("Expected an IllegalArgumentException because the minimum code point is negative");
        } catch (IllegalArgumentException e) {
            // Message produced by Validate: "Minimum code point -3302 is negative"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
