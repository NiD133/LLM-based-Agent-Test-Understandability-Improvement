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

    private static final int NON_WHITESPACE_CODE_POINT = -1628;
    private static final char[] DEFAULT_WHITESPACE_DELIMITERS = null;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        boolean isDelimiter = WordUtils.isDelimiter(NON_WHITESPACE_CODE_POINT, DEFAULT_WHITESPACE_DELIMITERS);

        assertFalse(isDelimiter);
    }
}
