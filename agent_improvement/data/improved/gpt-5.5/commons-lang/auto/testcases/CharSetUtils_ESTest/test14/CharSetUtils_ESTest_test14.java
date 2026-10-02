package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test14 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        String input = "@~j'\"_*}sm";
        String[] characterSetExpressions = new String[2];
        characterSetExpressions[1] = "ZS[4!;6>G|3UPaJfj";

        boolean containsCharacterFromSet = CharSetUtils.containsAny(input, characterSetExpressions);

        assertTrue(containsCharacterFromSet);
    }
}
