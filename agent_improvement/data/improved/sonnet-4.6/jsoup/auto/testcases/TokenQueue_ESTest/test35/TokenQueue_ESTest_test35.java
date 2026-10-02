package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test35 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that escapeCssIdentifier escapes a control character (backspace, U+0008) embedded
     * in a string as a CSS escaped code point: "\8 " (backslash + hex codepoint + trailing space).
     * Characters in the range U+0001–U+001F are serialized this way per the CSS spec.
     */
    @Test(timeout = 4000)
    public void test_escapeCssIdentifier_escapesEmbeddedControlCharacterAsCodepoint() throws Throwable {
        // '\b' is the backspace control character (U+0008), which falls in the range [U+0001, U+001F]
        // and must be CSS-escaped as "\8 " (backslash, hex digit, trailing space).
        String inputWithBackspace = "p\bmoD0M";

        String escaped = TokenQueue.escapeCssIdentifier(inputWithBackspace);

        // 'p' passes through unchanged; '\b' becomes "\8 "; the remaining "moD0M" is unchanged.
        assertEquals("p\\8 moD0M", escaped);
    }
}
