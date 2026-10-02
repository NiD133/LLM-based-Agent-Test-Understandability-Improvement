package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test08 extends CharSetUtils_ESTest_scaffolding {

    // delete() short-circuits when the input string is empty, returning "" regardless of the char-set array
    @Test(timeout = 4000)
    public void test_delete_emptyString_withNullFilledCharSetArray_returnsEmptyString() throws Throwable {
        String[] nullFilledCharSet = new String[9];
        String result = CharSetUtils.delete("", nullFilledCharSet);
        assertEquals("", result);
    }
}
