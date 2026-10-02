package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test16 extends WordUtils_ESTest_scaffolding {

    /**
     * When the delimiter array is null, {@link WordUtils#isDelimiter(char, char[])}
     * falls back to treating only whitespace characters as delimiters. The letter
     * 'J' is not whitespace, so it must not be reported as a delimiter.
     */
    @Test(timeout = 4000)
    public void isDelimiterReturnsFalseForLetterWhenDelimitersAreNull() throws Throwable {
        boolean isDelimiter = WordUtils.isDelimiter('J', (char[]) null);

        assertFalse("A non-whitespace character is not a delimiter when delimiters is null", isDelimiter);
    }
}
