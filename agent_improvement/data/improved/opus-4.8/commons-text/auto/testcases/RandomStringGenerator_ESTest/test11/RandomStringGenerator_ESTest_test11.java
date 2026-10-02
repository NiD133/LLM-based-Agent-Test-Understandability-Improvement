package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test11 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomStringGenerator.Builder#withinRange(int, int)}
     * rejects a range whose minimum code point is greater than its maximum.
     * Here the minimum is {@code Character.MAX_CODE_POINT} (1,114,111) while the
     * maximum is {@code 0}, so the builder must throw an
     * {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void withinRangeRejectsMinimumGreaterThanMaximum() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        int minimumCodePoint = 1114111; // Character.MAX_CODE_POINT
        int maximumCodePoint = 0;

        try {
            builder.withinRange(minimumCodePoint, maximumCodePoint);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "Minimum code point 1114111 is larger than maximum code point 0"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
