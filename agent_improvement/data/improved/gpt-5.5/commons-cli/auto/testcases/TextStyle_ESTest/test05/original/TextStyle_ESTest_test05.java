package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test05 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        TextStyle.Alignment textStyle_Alignment0 = TextStyle.Alignment.CENTER;
        TextStyle.Builder textStyle_Builder1 = textStyle_Builder0.setAlignment(textStyle_Alignment0);
        TextStyle textStyle0 = textStyle_Builder1.get();
        CharSequence charSequence0 = textStyle0.pad(true, "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}");
        assertEquals(Integer.MAX_VALUE, textStyle0.getMaxWidth());
        assertTrue(textStyle0.isScalable());
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}", charSequence0);
    }
}
