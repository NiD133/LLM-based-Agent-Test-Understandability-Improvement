package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test07 extends TextStyle_ESTest_scaffolding {

    /**
     * A right-aligned style with the default (unset) maximum width and zero indent
     * should leave any text untouched when padded, because there is no fixed width
     * to pad up to and no indent to prepend.
     */
    @Test(timeout = 4000)
    public void padWithUnsetMaxWidthReturnsTextUnchanged() throws Throwable {
        TextStyle rightAlignedStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.RIGHT)
                .get();

        String text = "TextStyle{LEFT, l:0, i:0, true, min:0, max:unset}";
        CharSequence paddedText = rightAlignedStyle.pad(true, text);

        assertEquals(text, paddedText);
        assertEquals(Integer.MAX_VALUE, rightAlignedStyle.getMaxWidth());
        assertTrue(rightAlignedStyle.isScalable());
    }
}
