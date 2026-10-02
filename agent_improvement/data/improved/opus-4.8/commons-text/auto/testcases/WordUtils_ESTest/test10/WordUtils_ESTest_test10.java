package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test10 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link WordUtils#swapCase(String)} inverts the case of each
     * letter while leaving non-letter characters untouched. Because the input
     * has no whitespace, only the very first letter is treated as the start of a
     * word: a lower-case word-start letter becomes title case, other lower-case
     * letters become upper case, and upper-case letters become lower case.
     */
    @Test(timeout = 4000)
    public void swapCaseInvertsLettersAndKeepsSymbols() throws Throwable {
        String input = "-l]U*[b,I?0";

        String result = WordUtils.swapCase(input);

        assertEquals("-L]u*[B,i?0", result);
    }
}
