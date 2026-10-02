package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test15 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that the single-quote matcher returns 0 (no match) when the
     * character at position 0 is the null character '\0' (not a single quote)
     * and the active buffer region is empty (bufferEnd == 0).
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        StrMatcher singleQuoteMatcher = StrMatcher.singleQuoteMatcher();

        // Buffer filled with default null characters ('\0'), none of which is a single quote
        char[] buffer = new char[4];

        int pos = 0;
        int bufferStart = 0;
        int bufferEnd = 0; // empty active region

        int matchLength = singleQuoteMatcher.isMatch(buffer, pos, bufferStart, bufferEnd);

        assertEquals(0, matchLength);
    }
}
