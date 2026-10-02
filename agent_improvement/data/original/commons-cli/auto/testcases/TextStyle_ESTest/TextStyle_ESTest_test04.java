package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test04 extends TextStyle_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TextStyle.Builder textStyle_Builder0 = TextStyle.builder();
        textStyle_Builder0.setMaxWidth(1132);
        TextStyle textStyle0 = textStyle_Builder0.get();
        textStyle0.pad(true, "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}");
        assertEquals(1132, textStyle_Builder0.getMaxWidth());
    }
}
