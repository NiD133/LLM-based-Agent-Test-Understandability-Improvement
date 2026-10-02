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
     * Padding empty text with a center-aligned style leaves the text empty,
     * and the style keeps its default scalable flag and unset (max int) width.
     */
    @Test(timeout = 4000)
    public void padEmptyTextWithCenterAlignmentReturnsEmpty() throws Throwable {
        TextStyle centeredStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        CharBuffer emptyText = CharBuffer.allocate(0);
        CharSequence paddedText = centeredStyle.pad(false, emptyText);

        assertEquals("", paddedText);
        assertTrue(centeredStyle.isScalable());
        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
    }
}
