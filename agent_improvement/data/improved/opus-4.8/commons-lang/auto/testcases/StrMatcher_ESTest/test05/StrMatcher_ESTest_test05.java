package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test05 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that {@link StrMatcher.StringMatcher#isMatch(char[], int, int, int)}
     * returns 0 (no match) when only the first character of the target string lines up
     * with the buffer and the following characters differ.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Buffer where only index 1 holds 'T'; the other slots stay as the default '\0'.
        char[] buffer = new char[4];
        buffer[1] = 'T';

        StrMatcher.StringMatcher stringMatcher =
                new StrMatcher.StringMatcher("The type must not be null");

        // Matching starts at index 1: 'T' matches, but the next stored char 'h'
        // does not match buffer[2] ('\0'), so the matcher reports no match.
        int matchedCount = stringMatcher.isMatch(buffer, 1, 6, 365);

        assertEquals(0, matchedCount);
    }
}
