package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test03 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that requesting a string whose minimum length is negative is rejected.
     * {@link RandomStringGenerator#generate(int, int)} validates its arguments via
     * Apache Commons Lang's {@code Validate}, which throws an
     * {@link IllegalArgumentException} when the minimum length is below zero.
     */
    @Test(timeout = 4000)
    public void generateWithNegativeMinimumLengthThrowsIllegalArgumentException() throws Throwable {
        final int negativeMinimumLength = -1;
        final int maximumLength = 1114111;
        RandomStringGenerator generator = RandomStringGenerator.builder().get();

        try {
            generator.generate(negativeMinimumLength, maximumLength);
            fail("Expected IllegalArgumentException because minimum length -1 is smaller than zero");
        } catch (IllegalArgumentException e) {
            // Message: "Minimum length -1 is smaller than zero."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
