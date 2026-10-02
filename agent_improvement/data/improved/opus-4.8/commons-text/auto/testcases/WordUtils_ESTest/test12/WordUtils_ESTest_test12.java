package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test12 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#isDelimiter(int, char[])} returns {@code true}
     * when the given code point matches one of the supplied delimiter characters.
     */
    @Test(timeout = 4000)
    public void isDelimiterReturnsTrueWhenCodePointMatchesADelimiter() throws Throwable {
        char[] delimiters = {'X', '\0'};

        boolean isDelimiter = WordUtils.isDelimiter((int) 'X', delimiters);

        assertTrue(isDelimiter);
    }
}
