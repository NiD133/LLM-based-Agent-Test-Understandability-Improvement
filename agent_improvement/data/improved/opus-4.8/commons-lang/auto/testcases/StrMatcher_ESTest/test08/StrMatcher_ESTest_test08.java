package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test08 extends StrMatcher_ESTest_scaffolding {

    /**
     * A char matcher reports a single matching character when the buffer position
     * holds the target char, even if the bufferStart/bufferEnd bounds are negative.
     * CharMatcher.isMatch only inspects buffer[pos], so the bounds are ignored here.
     */
    @Test(timeout = 4000)
    public void isMatchReturnsOneWhenCharAtPositionMatches() throws Throwable {
        StrMatcher charMatcher = StrMatcher.charMatcher('M');

        char[] buffer = new char[6];
        buffer[0] = 'M';

        int matchCount = charMatcher.isMatch(buffer, 0, -1, -1996);

        assertEquals(1, matchCount);
    }
}
