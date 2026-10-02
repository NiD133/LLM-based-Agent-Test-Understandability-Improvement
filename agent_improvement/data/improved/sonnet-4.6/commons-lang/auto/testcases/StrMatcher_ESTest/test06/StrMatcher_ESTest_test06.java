package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test06 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // An empty string matcher has no characters to compare, so it always returns 0
        StrMatcher.StringMatcher emptyStringMatcher = new StrMatcher.StringMatcher("");

        // A single-element buffer; actual content is irrelevant because the pattern is empty
        char[] buffer = new char[1];

        // Call isMatch with a negative position and a bufferEnd that is before bufferStart.
        // The empty matcher short-circuits (len == 0) and returns 0 regardless of bounds.
        int matchLength = emptyStringMatcher.isMatch(buffer, (-616), 4, (-616));
        assertEquals(0, matchLength);
    }
}
