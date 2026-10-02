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
     * Verifies that {@link RandomStringGenerator#generate(int)} rejects a negative
     * length by throwing an {@link IllegalArgumentException} raised through the
     * {@code org.apache.commons.lang3.Validate} guard.
     */
    @Test(timeout = 4000)
    public void generateWithNegativeLengthThrowsIllegalArgumentException() throws Throwable {
        RandomStringGenerator generator = RandomStringGenerator.builder().get();

        try {
            generator.generate(-882);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Length -882 is smaller than zero.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
