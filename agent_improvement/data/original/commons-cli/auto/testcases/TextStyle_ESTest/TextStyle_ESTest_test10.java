package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test10 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        textStyle_Builder0.setMaxWidth(133);
        TextStyle textStyle0 = textStyle_Builder0.get();
        CharSequence charSequence0 = textStyle0.pad(false, "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}");
        assertEquals(133, textStyle_Builder0.getMaxWidth());
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}                                                                                    ", charSequence0);
    }
}
