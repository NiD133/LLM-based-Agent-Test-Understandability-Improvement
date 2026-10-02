package org.apache.commons.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.io.Writer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnicodeEscaper_ESTest_test4 extends UnicodeEscaper_ESTest_scaffolding {

    // 0xD800 is the first Unicode high-surrogate code point
    private static final int HIGH_SURROGATE_START = 55296;
    // 0x048E is a Cyrillic code point, numerically less than HIGH_SURROGATE_START
    private static final int CYRILLIC_CODE_POINT = 1166;

    /**
     * When between() is called with an inverted range (low > high), no code point
     * satisfies the range condition, so translate() must return false (no escaping).
     */
    @Test(timeout = 4000)
    public void test4_translateReturnsFalseWhenBetweenRangeIsInverted() throws Throwable {
        // Create an escaper whose "between" range is inverted: low (0xD800) > high (0x048E)
        UnicodeEscaper escaperWithInvertedRange = UnicodeEscaper.between(HIGH_SURROGATE_START, CYRILLIC_CODE_POINT);

        StringWriter output = new StringWriter();

        // Translating the low boundary value should not escape anything because the range is inverted
        boolean wasEscaped = escaperWithInvertedRange.translate(HIGH_SURROGATE_START, (Writer) output);

        assertFalse("No escaping should occur when the between range is inverted (low > high)", wasEscaped);
    }
}
