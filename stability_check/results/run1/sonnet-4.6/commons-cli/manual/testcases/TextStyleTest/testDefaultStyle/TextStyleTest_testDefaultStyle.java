package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TextStyleTest_testDefaultStyle {

    /**
     * Verifies that TextStyle.DEFAULT is initialized with the expected default values:
     * LEFT alignment, scalable, zero left-pad, zero min-width, and unset max-width.
     */
    @Test
    void testDefaultStyle() {
        final TextStyle defaultStyle = TextStyle.DEFAULT;

        assertEquals(TextStyle.Alignment.LEFT, defaultStyle.getAlignment());
        assertTrue(defaultStyle.isScalable());
        assertEquals(0, defaultStyle.getLeftPad());
        assertEquals(0, defaultStyle.getMinWidth());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, defaultStyle.getMaxWidth());
    }
}
