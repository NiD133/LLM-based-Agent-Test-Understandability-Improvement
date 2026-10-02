package org.apache.commons.text;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test13 extends WordUtils_ESTest_scaffolding {

    /**
     * When the delimiter array is null, {@link WordUtils#isDelimiter(int, char[])}
     * falls back to treating whitespace code points as delimiters. A negative
     * code point is not whitespace, so the method should report it as not a delimiter.
     */
    @Test(timeout = 4000)
    public void isDelimiter_withNegativeCodePointAndNullDelimiters_returnsFalse() throws Throwable {
        final int nonWhitespaceCodePoint = -1628;

        boolean isDelimiter = WordUtils.isDelimiter(nonWhitespaceCodePoint, (char[]) null);

        assertFalse(isDelimiter);
    }
}
