package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test28 extends WordUtils_ESTest_scaffolding {

    private static final String MIXED_CASE_TEXT_WITH_SYMBOLS = ";)(5b_Sh4o|A8@";
    private static final String CAPITALIZE_FULLY_WITH_NUL_DELIMITERS = ";)(5b_sh4o|a8@";

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        char[] nulDelimiters = new char[2];

        String capitalized = WordUtils.capitalizeFully(MIXED_CASE_TEXT_WITH_SYMBOLS, nulDelimiters);

        assertEquals(CAPITALIZE_FULLY_WITH_NUL_DELIMITERS, capitalized);
    }
}
