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
     * Padding empty text with a CENTER alignment and an unset maximum width
     * should leave the text unchanged (no padding is added).
     */
    @Test(timeout = 4000)
    public void padCenterAlignedEmptyTextReturnsEmptyString() throws Throwable {
        TextStyle centeredStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        CharSequence emptyText = CharBuffer.allocate(0);
        CharSequence paddedText = centeredStyle.pad(false, emptyText);

        assertEquals("", paddedText);
        assertTrue(centeredStyle.isScalable());
        assertEquals(Integer.MAX_VALUE, centeredStyle.getMaxWidth());
    }
}
