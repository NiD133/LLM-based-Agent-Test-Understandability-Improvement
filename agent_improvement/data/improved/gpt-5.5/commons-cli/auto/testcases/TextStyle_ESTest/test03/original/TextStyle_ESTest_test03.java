package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test03 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TextStyle textStyle0 = TextStyle.DEFAULT;
        CharBuffer charBuffer0 = CharBuffer.allocate(0);
        CharSequence charSequence0 = textStyle0.pad(false, charBuffer0);
        assertEquals("", charSequence0);
    }
}
