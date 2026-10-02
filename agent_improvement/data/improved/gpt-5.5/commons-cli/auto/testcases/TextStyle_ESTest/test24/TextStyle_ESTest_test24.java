package org.apache.commons.cli.help;

import static org.junit.Assert.assertEquals;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test24 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        TextStyle defaultStyle = TextStyle.DEFAULT;
        TextStyle.Alignment actualAlignment = defaultStyle.getAlignment();

        assertEquals(TextStyle.Alignment.LEFT, actualAlignment);
    }
}
