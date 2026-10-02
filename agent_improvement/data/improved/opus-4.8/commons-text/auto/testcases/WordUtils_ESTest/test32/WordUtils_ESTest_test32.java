package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test32 extends WordUtils_ESTest_scaffolding {

    /**
     * When the upper limit (1693) is smaller than the lower limit (3451),
     * {@link WordUtils#abbreviate(String, int, int, String)} must reject the
     * arguments by throwing an {@link IllegalArgumentException} reported by
     * {@code org.apache.commons.lang3.Validate}.
     */
    @Test(timeout = 4000)
    public void abbreviateThrowsWhenUpperLimitBelowLowerLimit() throws Throwable {
        final String text = "M%VpyY9";
        final int lowerLimit = 3451;
        final int upperLimit = 1693;

        try {
            WordUtils.abbreviate(text, lowerLimit, upperLimit, text);
            fail("Expecting exception: IllegalArgumentException because upper value is less than lower value");
        } catch (IllegalArgumentException e) {
            // Validate.isTrue(upper >= lower || upper == -1, "upper value is less than lower value")
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
