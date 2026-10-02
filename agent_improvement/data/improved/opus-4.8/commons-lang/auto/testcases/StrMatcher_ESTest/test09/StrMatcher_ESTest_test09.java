package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test09 extends StrMatcher_ESTest_scaffolding {

    /**
     * stringMatcher("") is created from an empty string, so the factory returns
     * the "none" matcher, which never matches anything. isMatch therefore
     * returns 0 (no characters matched), regardless of the buffer or indices.
     */
    @Test(timeout = 4000)
    public void emptyStringMatcherNeverMatches() throws Throwable {
        StrMatcher emptyStringMatcher = StrMatcher.stringMatcher("");

        char[] buffer = new char[1];
        int matchedCharacters = emptyStringMatcher.isMatch(buffer, 1261, 34, 1261);

        assertEquals(0, matchedCharacters);
    }
}
