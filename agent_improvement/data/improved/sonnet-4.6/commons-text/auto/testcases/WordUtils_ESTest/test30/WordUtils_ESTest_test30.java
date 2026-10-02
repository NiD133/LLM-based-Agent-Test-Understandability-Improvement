package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test30 extends WordUtils_ESTest_scaffolding {

    /**
     * When upper=1, abbreviate truncates at the first character before the space,
     * then appends the appendToEnd suffix regardless of its content.
     * Input "Y oD/'" with lower=0, upper=1 finds a space at index 1,
     * so it takes "Y" (chars 0..min(1,1)) and appends the suffix.
     */
    @Test(timeout = 4000)
    public void test_abbreviate_upperLimitOne_appendsSuffixAfterFirstWord() throws Throwable {
        String input = "Y oD/'";
        int lower = 0;
        int upper = 1;
        String appendToEnd = "Upper value cannot be less than -1";

        String abbreviated = WordUtils.abbreviate(input, lower, upper, appendToEnd);

        assertEquals("YUpper value cannot be less than -1", abbreviated);
    }
}
