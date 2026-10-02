package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test02 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        TextStyle.Builder textStyle_Builder1 = textStyle_Builder0.setMaxWidth(3174);
        textStyle_Builder1.setIndent(3174);
        TextStyle textStyle0 = textStyle_Builder1.get();
        textStyle0.pad(true, "TextStyle{LEFT, l:0, i:3174, true, min:0, max:3174}");
        assertEquals(3174, textStyle_Builder1.getIndent());
        assertEquals(3174, textStyle_Builder0.getMaxWidth());
    }
}
