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
     * Verifies that padding an empty CharBuffer with CENTER alignment and no indent
     * returns an empty string, and that a freshly-built TextStyle retains its
     * default scalable flag and unbounded maximum width.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Build a TextStyle with CENTER alignment; all other settings stay at defaults
        // (scalable = true, maxWidth = UNSET_MAX_WIDTH = Integer.MAX_VALUE)
        TextStyle centeredStyle = TextStyle.builder()
                .setAlignment(TextStyle.Alignment.CENTER)
                .get();

        // An empty CharBuffer represents a zero-length text input
        CharBuffer emptyText = CharBuffer.allocate(0);

        // Padding empty text without indent should produce an empty string.
        // With CENTER alignment and maxWidth == UNSET_MAX_WIDTH the pad length
        // falls back to 0 (no indent requested), so the result is just the input.
        CharSequence paddedResult = centeredStyle.pad(false, emptyText);
        assertEquals("Padding empty text should yield an empty string", "", paddedResult);

        // Defaults inherited from the builder
        assertTrue("TextStyle should be scalable by default", centeredStyle.isScalable());
        assertEquals("maxWidth should equal UNSET_MAX_WIDTH (Integer.MAX_VALUE) when not set",
                Integer.MAX_VALUE, centeredStyle.getMaxWidth());
    }
}
