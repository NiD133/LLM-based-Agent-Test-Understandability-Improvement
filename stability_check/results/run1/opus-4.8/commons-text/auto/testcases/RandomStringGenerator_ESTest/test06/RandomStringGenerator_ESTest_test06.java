package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test06 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that requesting a string of negative length is rejected: the
     * generator delegates length validation to {@code Validate}, which throws
     * an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void generateWithNegativeLengthThrowsIllegalArgumentException() throws Throwable {
        final int negativeLength = -882;
        RandomStringGenerator generator = RandomStringGenerator.builder().get();

        try {
            generator.generate(negativeLength);
            fail("Expected an IllegalArgumentException because length " + negativeLength + " is smaller than zero.");
        } catch (IllegalArgumentException e) {
            // Message thrown by Validate: "Length -882 is smaller than zero."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
