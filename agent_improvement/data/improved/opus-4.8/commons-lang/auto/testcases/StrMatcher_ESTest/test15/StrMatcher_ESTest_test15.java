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
     * The single-quote matcher reports a match only when the character at the
     * requested position is a single quote ('). Here the buffer holds the
     * default char value ('\0'), so isMatch must report no match (0).
     */
    @Test(timeout = 4000)
    public void testIsMatchReturnsZeroWhenCharacterIsNotASingleQuote() throws Throwable {
        StrMatcher singleQuoteMatcher = StrMatcher.singleQuoteMatcher();
        char[] buffer = new char[4];

        int matchCount = singleQuoteMatcher.isMatch(buffer, 0, 0, 0);

        assertEquals(0, matchCount);
    }
}
