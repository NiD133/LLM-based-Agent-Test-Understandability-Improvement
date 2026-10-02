package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test22 extends StrMatcher_ESTest_scaffolding {

    /**
     * When the start position plus the matcher's length would run past the
     * declared end of the active buffer, {@link StrMatcher.StringMatcher#isMatch}
     * reports no match (zero matching characters) without reading the buffer.
     */
    @Test(timeout = 4000)
    public void isMatchReturnsZeroWhenMatchWouldExceedBufferEnd() throws Throwable {
        // Matcher looks for the single-character string "^".
        StrMatcher.StringMatcher caretMatcher = new StrMatcher.StringMatcher("^");

        char[] buffer = new char[4];
        int startPosition = 2860;
        int bufferStart = 0;
        int bufferEnd = 2860;

        // startPosition + length("^") = 2861, which is beyond bufferEnd (2860),
        // so the match is short-circuited to zero.
        int matchLength = caretMatcher.isMatch(buffer, startPosition, bufferStart, bufferEnd);

        assertEquals(0, matchLength);
    }
}
