package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test00 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        TextStyle textStyle0 = TextStyle.DEFAULT;
        String string0 = textStyle0.toString();
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}", string0);
    }
}
