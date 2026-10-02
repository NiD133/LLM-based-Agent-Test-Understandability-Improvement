package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test25 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_defaultTextStyle_isScalable() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;
        assertTrue("TextStyle.DEFAULT should be scalable by default", defaultStyle.isScalable());
    }
}
