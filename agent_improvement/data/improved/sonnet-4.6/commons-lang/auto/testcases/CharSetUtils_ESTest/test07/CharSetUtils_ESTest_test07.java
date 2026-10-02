package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test07 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_keep_emptyString_returnsEmpty() throws Throwable {
        // An empty input string should always produce an empty result,
        // regardless of the character set array contents (all null elements here).
        String[] charSetWithNullEntries = new String[9];
        String result = CharSetUtils.keep("", charSetWithNullEntries);
        assertEquals("", result);
    }
}
