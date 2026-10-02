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
     * CharMatcher only inspects buffer[pos] and ignores bufferStart/bufferEnd,
     * so it must return 1 even when those boundary values are out-of-range.
     */
    @Test(timeout = 4000)
    public void test08_charMatcherIgnoresBufferBoundsWhenMatchingAtPosition() throws Throwable {
        StrMatcher charMatcher = StrMatcher.charMatcher('M');

        char[] buffer = new char[6];
        buffer[0] = 'M';

        // Pass invalid buffer bounds; CharMatcher should still match 'M' at pos 0
        int matchLength = charMatcher.isMatch(buffer, 0, -1, -1996);

        assertEquals(1, matchLength);
    }
}
