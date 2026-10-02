package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test09 extends StrMatcher_ESTest_scaffolding {

    // StrMatcher.stringMatcher("") returns the NONE_MATCHER singleton because an empty string
    // matches nothing; NONE_MATCHER.isMatch() always returns 0 regardless of arguments,
    // including out-of-bounds positions that would throw for other matcher implementations.
    @Test(timeout = 4000)
    public void test_emptyStringMatcher_alwaysReturnsNoMatch() throws Throwable {
        char[] singleCharBuffer = new char[1];
        StrMatcher emptyStringMatcher = StrMatcher.stringMatcher("");

        int matchLength = emptyStringMatcher.isMatch(singleCharBuffer, 1261, 34, 1261);

        assertEquals(0, matchLength);
    }
}
