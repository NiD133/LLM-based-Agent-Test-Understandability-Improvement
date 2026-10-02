package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test13 extends WordUtils_ESTest_scaffolding {

    // A non-whitespace code point; negative values are not valid Unicode but the method handles them gracefully
    private static final int NON_WHITESPACE_CODE_POINT = -1628;

    @Test(timeout = 4000)
    public void isDelimiter_nullDelimiters_nonWhitespaceCodePoint_returnsFalse() throws Throwable {
        // When delimiters is null, isDelimiter falls back to Character.isWhitespace(codePoint).
        // A negative code point is not whitespace, so the result must be false.
        boolean result = WordUtils.isDelimiter(NON_WHITESPACE_CODE_POINT, (char[]) null);
        assertFalse(result);
    }
}
