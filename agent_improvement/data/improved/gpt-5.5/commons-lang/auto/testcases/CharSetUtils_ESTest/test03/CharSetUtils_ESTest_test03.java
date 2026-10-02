package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test03 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        String[] characterSet = new String[2];
        characterSet[1] = "ZS[4!;6>G|3UPaJfj";

        String squeezedText = CharSetUtils.squeeze("offset cannot be negative", characterSet);

        assertEquals("ofset cannot be negative", squeezedText);
    }
}
