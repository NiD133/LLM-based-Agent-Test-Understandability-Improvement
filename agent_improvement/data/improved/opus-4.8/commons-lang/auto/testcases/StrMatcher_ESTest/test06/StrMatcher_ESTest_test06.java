package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test06 extends StrMatcher_ESTest_scaffolding {

    /**
     * A StringMatcher built from an empty string has nothing to match, so its
     * match length is zero. With a zero-length target, isMatch short-circuits to
     * return 0 without ever reading the buffer, even when given out-of-range
     * position and buffer indices.
     */
    @Test(timeout = 4000)
    public void testEmptyStringMatcherReturnsZero() throws Throwable {
        StrMatcher.StringMatcher emptyStringMatcher = new StrMatcher.StringMatcher("");
        char[] buffer = new char[1];

        int matchedCount = emptyStringMatcher.isMatch(buffer, -616, 4, -616);

        assertEquals(0, matchedCount);
    }
}
