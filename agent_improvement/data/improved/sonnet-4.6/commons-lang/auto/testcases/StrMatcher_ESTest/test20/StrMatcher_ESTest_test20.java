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
     * Verifies that TrimMatcher returns 0 (no match) when the character at the given
     * position is a non-whitespace character ('D', ASCII 68 > 32), even when bufferEnd
     * equals bufferStart (empty active range). TrimMatcher matches characters with
     * code point <= 32 (whitespace), so 'D' must not match.
     */
    @Test(timeout = 4000)
    public void test20_trimMatcher_doesNotMatchNonWhitespaceCharacter() throws Throwable {
        StrMatcher trimMatcher = StrMatcher.trimMatcher();

        // Buffer with a non-whitespace character at index 0
        char[] buffer = new char[5];
        buffer[0] = 'D'; // ASCII 68, greater than 32 (trim threshold)

        int matchLength = trimMatcher.isMatch(buffer, 0, 0, 0);

        assertEquals(0, matchLength);
    }
}
