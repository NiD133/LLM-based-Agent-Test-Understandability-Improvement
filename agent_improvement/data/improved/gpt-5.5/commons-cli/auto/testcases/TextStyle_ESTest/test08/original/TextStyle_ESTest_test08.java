package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test08 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        TextStyle.Alignment textStyle_Alignment0 = TextStyle.Alignment.CENTER;
        TextStyle.Builder textStyle_Builder1 = textStyle_Builder0.setAlignment(textStyle_Alignment0);
        CharBuffer charBuffer0 = CharBuffer.allocate(0);
        TextStyle textStyle0 = textStyle_Builder1.get();
        CharSequence charSequence0 = textStyle0.pad(false, charBuffer0);
        assertEquals("", charSequence0);
        assertTrue(textStyle0.isScalable());
        assertEquals(Integer.MAX_VALUE, textStyle0.getMaxWidth());
    }
}
