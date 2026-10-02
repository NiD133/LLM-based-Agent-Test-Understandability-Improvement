package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test24 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        TextStyle textStyle0 = TextStyle.DEFAULT;
        TextStyle.Alignment textStyle_Alignment0 = textStyle0.getAlignment();
        assertEquals(TextStyle.Alignment.LEFT, textStyle_Alignment0);
    }
}
