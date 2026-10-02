package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test01 extends TextStyle_ESTest_scaffolding {

    /**
     * Verifies that a custom maximum width set on the builder is retained by the
     * builder and is reflected in the {@code toString()} of the built TextStyle,
     * while every other property keeps its default value.
     */
    @Test(timeout = 4000)
    public void buildWithCustomMaxWidth_retainsValueAndRendersToString() throws Throwable {
        final int customMaxWidth = 3174;

        TextStyle.Builder builder = TextStyle.builder();
        builder.setMaxWidth(customMaxWidth);
        TextStyle textStyle = builder.get();

        // The builder reports back the maximum width it was given.
        assertEquals(customMaxWidth, builder.getMaxWidth());

        // toString shows the default alignment/padding/indent/scalable/min values
        // together with the custom maximum width.
        assertEquals("TextStyle{LEFT, l:0, i:0, true, min:0, max:3174}", textStyle.toString());
    }
}
