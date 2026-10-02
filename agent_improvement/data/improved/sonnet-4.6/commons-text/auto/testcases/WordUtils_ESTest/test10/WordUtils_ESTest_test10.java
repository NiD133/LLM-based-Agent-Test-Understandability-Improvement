package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test10 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that swapCase flips the case of each letter while leaving
     * punctuation characters and digits unchanged.
     * Lowercase letters become uppercase (or title-case at word start),
     * and uppercase letters become lowercase.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // String with interleaved lowercase letters, uppercase letters,
        // punctuation, and a digit
        String mixedInput = "-l]U*[b,I?0";

        // Each letter's case is swapped; non-letter characters are preserved
        String expectedSwapped = "-L]u*[B,i?0";

        String result = WordUtils.swapCase(mixedInput);

        assertEquals(expectedSwapped, result);
    }
}
