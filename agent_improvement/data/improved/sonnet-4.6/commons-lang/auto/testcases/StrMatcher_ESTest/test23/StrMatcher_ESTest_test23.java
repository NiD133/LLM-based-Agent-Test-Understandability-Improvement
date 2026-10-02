package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test23 extends StrMatcher_ESTest_scaffolding {

    // charSetMatcher with a single-element array returns a non-null matcher
    // (the CUT converts a single-char array into a CharMatcher instead of a CharSetMatcher)
    @Test(timeout = 4000)
    public void test_charSetMatcher_withSingleCharArray_returnsNonNullMatcher() throws Throwable {
        char[] singleNullChar = new char[1];
        StrMatcher matcher = StrMatcher.charSetMatcher(singleNullChar);
        assertNotNull(matcher);
    }
}
