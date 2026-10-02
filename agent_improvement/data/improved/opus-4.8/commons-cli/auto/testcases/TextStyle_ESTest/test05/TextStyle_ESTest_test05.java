package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test05 extends TextStyle_ESTest_scaffolding {

    /**
     * A CENTER-aligned style with default width/indent settings should leave the
     * text untouched: with maxWidth unset and indent 0, padding adds no spaces,
     * so pad() returns the original text. Also confirms the default maxWidth and
     * scalable values carried over from the builder.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        TextStyle centeredStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        String text = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        CharSequence padded = centeredStyle.pad(true, text);

        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
        assertTrue(centeredStyle.isScalable());
        assertEquals(text, padded);
    }
}
