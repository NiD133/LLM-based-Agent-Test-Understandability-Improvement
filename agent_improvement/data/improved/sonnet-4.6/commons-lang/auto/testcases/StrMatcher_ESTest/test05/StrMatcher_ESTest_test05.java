package org.apache.commons.lang3.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrMatcher_ESTest_test05 extends StrMatcher_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Buffer of 4 chars: index 1 = 'T', rest are null chars ('\0')
        char[] buffer = new char[4];
        buffer[1] = 'T';

        // Matcher looks for the full string "The type must not be null"
        StrMatcher.StringMatcher stringMatcher = new StrMatcher.StringMatcher("The type must not be null");

        // Starting at pos=1 ('T'), the second char buffer[2]='\0' does not match 'h',
        // so the matcher returns 0 (no match)
        int matchLength = stringMatcher.isMatch(buffer, 1, 6, 365);
        assertEquals(0, matchLength);
    }
}
