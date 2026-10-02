package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test11 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that a CharSetMatcher built from an array of null characters ('\0')
     * matches a buffer whose first character is also '\0'.
     *
     * The char array is default-initialized to all '\0', so the matcher's character
     * set contains '\0'. Calling isMatch at position 0 should return 1, confirming
     * a single-character match.
     */
    @Test(timeout = 4000)
    public void test_charSetMatcherWithNullChars_matchesNullCharAtPositionZero() throws Throwable {
        // A default char array of length 6 is filled with null characters ('\0')
        char[] bufferOfNullChars = new char[6];

        // Build a CharSetMatcher whose set consists of the null characters in the array
        StrMatcher nullCharSetMatcher = StrMatcher.charSetMatcher(bufferOfNullChars);

        // Position 0 in the buffer is '\0', which belongs to the matcher's set
        int matchLength = nullCharSetMatcher.isMatch(bufferOfNullChars, 0);

        // A single character was matched
        assertEquals(1, matchLength);
    }
}
