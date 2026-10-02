package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test14 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isDelimiter_negativeCodePoint_notInDelimiterArray_returnsFalse() throws Throwable {
        // A single-element delimiter array containing the null character '\0'
        char[] delimiters = new char[1];

        // A negative code point (-716) is not a valid Unicode value and does not match '\0'
        boolean result = WordUtils.isDelimiter((-716), delimiters);

        assertFalse(result);
    }
}
