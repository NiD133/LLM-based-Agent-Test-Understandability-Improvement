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
    public void test_toStringReflectsCustomMaxWidth() throws Throwable {
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Builder builderWithMaxWidth = builder.setMaxWidth(3174);
        TextStyle style = builderWithMaxWidth.get();

        String styleDescription = style.toString();

        assertEquals(3174, builder.getMaxWidth());
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:3174}", styleDescription);
    }
}
