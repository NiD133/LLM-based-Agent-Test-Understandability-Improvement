package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test04 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04_keep_returnsEmptyString_whenNoCharsInInputMatchTheGivenSet() throws Throwable {
        // Build a sparse set array: 9 slots, only index 3 has a value that does not include 'E'
        String[] charSetArray = new String[9];
        charSetArray[3] = "!f0C7\"CJoqlK";

        String result = CharSetUtils.keep("E", charSetArray);

        assertEquals("", result);
    }
}
