package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test21 extends WordUtils_ESTest_scaffolding {

    /**
     * When an empty delimiter array is passed, no characters act as word separators,
     * so only the very first character of the string is capitalized.
     */
    @Test(timeout = 4000)
    public void test21_capitalizeWithEmptyDelimiters_onlyFirstCharCapitalized() throws Throwable {
        char[] emptyDelimiters = new char[0];
        String result = WordUtils.capitalize("upper value is less than lower value", emptyDelimiters);
        assertEquals("Upper value is less than lower value", result);
    }
}
