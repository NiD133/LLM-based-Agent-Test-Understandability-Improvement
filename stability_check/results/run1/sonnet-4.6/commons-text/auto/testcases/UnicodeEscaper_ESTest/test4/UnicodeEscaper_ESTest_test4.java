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

    // Code point U+D800 is the first high surrogate; used as the "low" boundary here
    private static final int SURROGATE_HIGH_START = 55296; // 0xD800
    // Code point U+048E is a Cyrillic Extended letter; used as the "high" boundary here
    private static final int CYRILLIC_EXTENDED_CHAR = 1166;  // 0x048E

    /**
     * When between(low, high) is called with an inverted range (low > high),
     * a code point equal to the lower bound is outside the valid range and
     * translate() should return false (no escaping performed).
     */
    @Test(timeout = 4000)
    public void test_translateWithInvertedRange_returnsFalse() throws Throwable {
        // Create an escaper for the inverted range [55296, 1166] — low > high
        UnicodeEscaper escaper = UnicodeEscaper.between(SURROGATE_HIGH_START, CYRILLIC_EXTENDED_CHAR);

        StringWriter output = new StringWriter();

        // Translating a code point equal to the lower boundary of the inverted range:
        // the condition (codePoint > above) is true, so translate() returns false
        boolean wasEscaped = escaper.translate(SURROGATE_HIGH_START, (Writer) output);

        assertFalse(wasEscaped);
    }
}
