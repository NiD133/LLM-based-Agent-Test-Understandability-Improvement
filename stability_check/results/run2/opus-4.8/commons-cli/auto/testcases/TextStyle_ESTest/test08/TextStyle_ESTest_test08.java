package org.apache.commons.cli.help;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TextStyle_ESTest_test08 extends TextStyle_ESTest_scaffolding {

    /**
     * Padding empty text with a center-aligned style returns an empty result,
     * and the style keeps the builder defaults (scalable, unset maximum width).
     */
    @Test(timeout = 4000)
    public void padEmptyTextWithCenterAlignmentReturnsEmptyString() throws Throwable {
        TextStyle centerAlignedStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        CharBuffer emptyText = CharBuffer.allocate(0);
        CharSequence paddedText = centerAlignedStyle.pad(false, emptyText);

        assertEquals("", paddedText);
        assertTrue(centerAlignedStyle.isScalable());
        assertEquals(Integer.MAX_VALUE, centerAlignedStyle.getMaxWidth());
    }
}
