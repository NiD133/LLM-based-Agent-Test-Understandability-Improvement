package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test20 extends StrMatcher_ESTest_scaffolding {

    /**
     * The trim matcher matches whitespace-style characters (code point &lt;= 32).
     * Here the character at the start position is 'D' (code point 68), which is
     * not whitespace, so isMatch should report zero matching characters.
     */
    @Test(timeout = 4000)
    public void trimMatcherReportsNoMatchForNonWhitespaceCharacter() throws Throwable {
        StrMatcher trimMatcher = StrMatcher.trimMatcher();

        char[] buffer = new char[5];
        buffer[0] = 'D';
        int startPosition = 0;
        int bufferStart = 0;
        int bufferEnd = 0;

        int matchCount = trimMatcher.isMatch(buffer, startPosition, bufferStart, bufferEnd);

        assertEquals(0, matchCount);
    }
}
