package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NumericEntityUnescaper_ESTest_test6 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * Verifies that a bare '&' character (not a valid numeric entity) is returned
     * unchanged when no options are supplied (defaults to semiColonRequired).
     *
     * A valid numeric entity requires at least "&#" followed by digits, so a
     * single '&' is too short to match and must pass through as-is.
     */
    @Test(timeout = 4000)
    public void test6_bareAmpersandPassesThroughUnchanged() throws Throwable {
        // Arrange: unescaper with default behaviour (empty options → semiColonRequired)
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);

        // Input is a single '&' wrapped in a CharBuffer — too short to be a numeric entity
        char[] singleAmpersand = new char[] { '&' };
        CharBuffer inputBuffer = CharBuffer.wrap(singleAmpersand);

        // Act
        String result = unescaper.translate((CharSequence) inputBuffer);

        // Assert: the lone '&' is returned unchanged
        assertEquals("&", result);
    }
}
