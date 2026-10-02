package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TextStyleTest_testDefaultStyle {

    @Test
    @DisplayName("TextStyle.DEFAULT should carry the out-of-the-box builder defaults")
    void testDefaultStyle() {
        final TextStyle defaultStyle = TextStyle.DEFAULT;

        // alignment defaults to LEFT
        assertEquals(TextStyle.Alignment.LEFT, defaultStyle.getAlignment());

        // scalable is true by default, allowing width adjustments
        assertTrue(defaultStyle.isScalable());

        // no left padding applied by default
        assertEquals(0, defaultStyle.getLeftPad());

        // no minimum width constraint by default
        assertEquals(0, defaultStyle.getMinWidth());

        // maximum width is unset (Integer.MAX_VALUE) by default
        assertEquals(TextStyle.UNSET_MAX_WIDTH, defaultStyle.getMaxWidth());
    }
}
