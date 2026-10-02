package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test07 extends StrMatcher_ESTest_scaffolding {

    /**
     * Verifies that charSetMatcher returns 0 (no match) when the character at the
     * given position is a null character ('\0'), which is not present in the matcher's
     * character set.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Buffer of 6 null characters ('\0')
        char[] buffer = new char[6];

        // Matcher recognises any character in "-%nVf!yZM0Csg>" — null char is not among them
        StrMatcher charSetMatcher = StrMatcher.charSetMatcher("-%nVf!yZM0Csg>");

        // bufferStart (32) and bufferEnd (0) are intentionally out-of-range values carried
        // over from the generated test; CharSetMatcher ignores them and matches solely on
        // the character value at the given position.
        int matchLength = charSetMatcher.isMatch(buffer, 0, 32, 0);

        // '\0' is not in the set, so no match
        assertEquals(0, matchLength);
    }
}
