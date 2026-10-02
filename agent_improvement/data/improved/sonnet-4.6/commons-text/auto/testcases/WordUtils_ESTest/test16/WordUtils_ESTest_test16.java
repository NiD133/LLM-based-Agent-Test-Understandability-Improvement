package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test16 extends WordUtils_ESTest_scaffolding {

    // When delimiters is null, isDelimiter falls back to Character.isWhitespace.
    // 'J' is not whitespace, so the method should return false.
    @Test(timeout = 4000)
    public void test_isDelimiter_nonWhitespaceChar_nullDelimiters_returnsFalse() throws Throwable {
        boolean isJADelimiter = WordUtils.isDelimiter('J', (char[]) null);
        assertFalse(isJADelimiter);
    }
}
