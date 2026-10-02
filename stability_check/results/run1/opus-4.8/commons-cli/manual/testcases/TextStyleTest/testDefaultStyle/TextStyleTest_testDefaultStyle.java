package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the property values of the shared {@link TextStyle#DEFAULT} instance.
 */
public class TextStyleTest_testDefaultStyle {

    @Test
    void testDefaultStyle() {
        final TextStyle defaultStyle = TextStyle.DEFAULT;

        // The default style left-justifies text.
        assertEquals(TextStyle.Alignment.LEFT, defaultStyle.getAlignment());
        // The default style allows its width to be scaled.
        assertTrue(defaultStyle.isScalable());
        // Padding and width defaults are all unconstrained.
        assertEquals(0, defaultStyle.getLeftPad());
        assertEquals(0, defaultStyle.getMinWidth());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, defaultStyle.getMaxWidth());
    }
}
