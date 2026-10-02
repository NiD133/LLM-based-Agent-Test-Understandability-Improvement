package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test07 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#uncapitalize(String)} leaves a String unchanged
     * when it contains no whitespace-separated word that starts with an upper-case letter.
     *
     * <p>The input ".*\b" (the characters dot, asterisk and a backspace) has no
     * whitespace delimiters and no letters to lower-case, so the returned String is
     * identical to the input.</p>
     */
    @Test(timeout = 4000)
    public void uncapitalizeStringWithoutCapitalLettersReturnsSameString() throws Throwable {
        String input = ".*\b";

        String result = WordUtils.uncapitalize(input);

        assertEquals(".*\b", result);
    }
}
