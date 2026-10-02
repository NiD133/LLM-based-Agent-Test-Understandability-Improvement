package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test02 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that a TextStyle.Builder remembers the indent and maxWidth values
     * it was given, and that building the style and padding text with it runs
     * without error.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        final int width = 3174;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(width);
        builder.setIndent(width);

        TextStyle textStyle = builder.get();
        textStyle.pad(true, "TextStyle{LEFT, l:0, i:3174, true, min:0, max:3174}");

        assertEquals(width, builder.getIndent());
        assertEquals(width, builder.getMaxWidth());
    }
}
