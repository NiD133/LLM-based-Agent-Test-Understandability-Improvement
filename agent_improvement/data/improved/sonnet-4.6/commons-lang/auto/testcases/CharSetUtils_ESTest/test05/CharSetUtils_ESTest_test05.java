package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test05 extends CharSetUtils_ESTest_scaffolding {

    // keep() returns "" when the set array contains only a null element,
    // because a null entry is treated as an empty (all-null) set definition.
    @Test(timeout = 4000)
    public void test_keep_returnsEmpty_whenSetArrayContainsOnlyNullEntry() throws Throwable {
        String[] setWithSingleNullEntry = new String[1];
        String result = CharSetUtils.keep("=11X|n;V_", setWithSingleNullEntry);
        assertEquals("", result);
    }
}
