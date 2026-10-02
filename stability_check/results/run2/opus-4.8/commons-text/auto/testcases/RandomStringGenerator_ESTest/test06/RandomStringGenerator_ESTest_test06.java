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
     * Requesting a string with a negative length must be rejected: {@code generate}
     * delegates its argument validation to Apache Commons Lang's {@code Validate},
     * which throws an {@link IllegalArgumentException} for the invalid length.
     */
    @Test(timeout = 4000)
    public void generateWithNegativeLengthThrowsIllegalArgumentException() throws Throwable {
        RandomStringGenerator generator = RandomStringGenerator.builder().get();
        int negativeLength = -882;

        try {
            generator.generate(negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Length -882 is smaller than zero."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
