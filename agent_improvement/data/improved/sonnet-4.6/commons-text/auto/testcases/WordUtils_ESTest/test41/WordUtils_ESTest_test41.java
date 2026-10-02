package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test41 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_uncapitalize_whitespaceOnlyString_returnsUnchanged() throws Throwable {
        // A string containing only a space has no word-initial character to uncapitalize,
        // so the result must equal the original input.
        String whitespaceOnly = " ";
        String result = WordUtils.uncapitalize(whitespaceOnly);
        assertEquals(whitespaceOnly, result);
    }
}
