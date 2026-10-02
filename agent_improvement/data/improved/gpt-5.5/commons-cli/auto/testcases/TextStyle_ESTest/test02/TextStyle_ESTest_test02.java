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
        final int widthAndIndent = 3174;
        final String textToPad = "TextStyle{LEFT, l:0, i:3174, true, min:0, max:3174}";

        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Builder configuredBuilder = builder.setMaxWidth(widthAndIndent);
        configuredBuilder.setIndent(widthAndIndent);

        TextStyle style = configuredBuilder.get();
        style.pad(true, textToPad);

        assertEquals(widthAndIndent, configuredBuilder.getIndent());
        assertEquals(widthAndIndent, builder.getMaxWidth());
    }
}
