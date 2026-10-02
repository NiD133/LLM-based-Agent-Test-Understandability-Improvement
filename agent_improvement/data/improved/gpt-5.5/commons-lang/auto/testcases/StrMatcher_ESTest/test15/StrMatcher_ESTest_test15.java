package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test15 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        StrMatcher singleQuoteMatcher = StrMatcher.singleQuoteMatcher();
        char[] bufferWithDefaultCharacters = new char[4];
        int matchLength = singleQuoteMatcher.isMatch(bufferWithDefaultCharacters, 0, 0, 0);
        assertEquals(0, matchLength);
    }
}
