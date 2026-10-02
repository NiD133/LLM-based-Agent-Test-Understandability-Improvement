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

    /**
     * Verifies that a TextStyle built with maxWidth and indent both set to 3174
     * correctly retains those values on the builder, and that pad() returns the
     * input text unchanged when it is longer than maxWidth.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Build a TextStyle with maxWidth=3174 and indent=3174
        TextStyle.Builder builder = TextStyle.builder();
        TextStyle.Builder builderWithMaxWidth = builder.setMaxWidth(3174);
        builderWithMaxWidth.setIndent(3174);
        TextStyle style = builderWithMaxWidth.get();

        // The text length (53) is less than maxWidth (3174), so pad() appends spaces;
        // the string itself happens to be the toString() representation of the style.
        style.pad(true, "TextStyle{LEFT, l:0, i:3174, true, min:0, max:3174}");

        // setMaxWidth returns "this", so builder and builderWithMaxWidth are the same object
        assertEquals(3174, builderWithMaxWidth.getIndent());
        assertEquals(3174, builder.getMaxWidth());
    }
}
