package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test00 extends CharSetUtils_ESTest_scaffolding {

    // squeeze("...") with a charset that contains no dot characters leaves the string unchanged
    @Test(timeout = 4000)
    public void test_squeeze_withCharsetNotContainingInputChars_returnsOriginalString() throws Throwable {
        String[] charsetSet = new String[1];
        charsetSet[0] = "Minimum abbreviation width with offset is %d";
        String result = CharSetUtils.squeeze("...", charsetSet);
        assertEquals("...", result);
    }
}
