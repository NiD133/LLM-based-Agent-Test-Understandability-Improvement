package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test07 extends WordUtils_ESTest_scaffolding {

    /**
     * Verifies that uncapitalize leaves a string unchanged when it contains only
     * non-letter characters (regex symbols and a backspace), since there are no
     * word-initial letters to lowercase.
     */
    @Test(timeout = 4000)
    public void test_uncapitalize_stringWithOnlyNonLetterChars_returnsUnchanged() throws Throwable {
        String specialCharsInput = ".*\b";

        String result = WordUtils.uncapitalize(specialCharsInput);

        assertEquals(".*\b", result);
    }
}
