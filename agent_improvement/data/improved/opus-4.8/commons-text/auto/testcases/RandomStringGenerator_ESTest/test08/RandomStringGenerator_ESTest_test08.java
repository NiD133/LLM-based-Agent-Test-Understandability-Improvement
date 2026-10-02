package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test08 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that {@link RandomStringGenerator.Builder#withinRange(int, int)} rejects a
     * maximum code point that exceeds {@link Character#MAX_CODE_POINT}.
     *
     * The value 2064888123 is far above the largest valid Unicode code point, so the builder
     * must reject it with an {@link IllegalArgumentException} raised by Apache Commons Lang's
     * {@code Validate} helper.
     */
    @Test(timeout = 4000)
    public void withinRange_rejectsCodePointAboveMaxCodePoint() throws Throwable {
        RandomStringGenerator.Builder builder = new RandomStringGenerator.Builder();

        // Code point well beyond Character.MAX_CODE_POINT (0x10FFFF = 1114111).
        int codePointAboveMax = 2064888123;

        try {
            builder.withinRange(codePointAboveMax, codePointAboveMax);
            fail("Expected IllegalArgumentException because the code point exceeds Character.MAX_CODE_POINT");
        } catch (IllegalArgumentException e) {
            // Message: "Value 2064888123 is larger than Character.MAX_CODE_POINT."
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
