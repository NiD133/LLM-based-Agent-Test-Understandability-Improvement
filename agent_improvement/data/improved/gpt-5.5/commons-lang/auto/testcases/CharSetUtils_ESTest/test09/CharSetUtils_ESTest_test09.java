package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test09 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final String input = "YhY[";
        final String nonMatchingCharacterSet = "Iyc@#m)6#tX";
        final String[] characterSetsToDelete = new String[9];
        characterSetsToDelete[7] = nonMatchingCharacterSet;

        final String result = CharSetUtils.delete(input, characterSetsToDelete);

        assertEquals("YhY[", result);
    }
}
