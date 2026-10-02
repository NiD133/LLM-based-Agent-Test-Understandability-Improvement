package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test01 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        TextStyle.Builder textStyle_Builder1 = textStyle_Builder0.setMaxWidth(3174);
        TextStyle textStyle0 = textStyle_Builder1.get();
        String string0 = textStyle0.toString();
        assertEquals(3174, textStyle_Builder0.getMaxWidth());
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:3174}", string0);
    }
}
