package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test11 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        char[] nullCharacters = new char[6];
        StrMatcher nullCharacterMatcher = StrMatcher.charSetMatcher(nullCharacters);

        int matchLength = nullCharacterMatcher.isMatch(nullCharacters, 0);

        assertEquals(1, matchLength);
    }
}
