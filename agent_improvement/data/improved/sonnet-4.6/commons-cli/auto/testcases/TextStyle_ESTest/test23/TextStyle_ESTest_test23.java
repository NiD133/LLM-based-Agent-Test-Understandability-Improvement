package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test23 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_defaultTextStyle_hasZeroLeftPad() throws Throwable {
        int leftPad = TextStyle.DEFAULT.getLeftPad();
        assertEquals("TextStyle.DEFAULT should have no left padding (0)", 0, leftPad);
    }
}
