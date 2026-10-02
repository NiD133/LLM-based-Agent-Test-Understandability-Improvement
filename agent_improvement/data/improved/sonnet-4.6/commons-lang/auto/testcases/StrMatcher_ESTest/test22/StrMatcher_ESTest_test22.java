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
     * Verifies that StringMatcher returns 0 when the match position is exactly at
     * the buffer end boundary (pos + matchLength > bufferEnd), making a match impossible.
     */
    @Test(timeout = 4000)
    public void test_stringMatcher_returnsNoMatch_whenPositionIsAtBufferEnd() throws Throwable {
        StrMatcher.StringMatcher caretMatcher = new StrMatcher.StringMatcher("^");
        char[] buffer = new char[4];
        int bufferEnd = 2860;
        int posAtBufferEnd = 2860; // pos == bufferEnd, so pos + len("^") exceeds bufferEnd

        int matchResult = caretMatcher.isMatch(buffer, posAtBufferEnd, 0, bufferEnd);

        assertEquals(0, matchResult);
    }
}
