package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TextStyleTest_testDefaultStyle {

    @Test
    void testDefaultStyle() {
        final TextStyle underTest = TextStyle.DEFAULT;

        assertEquals(TextStyle.Alignment.LEFT, underTest.getAlignment());
        assertTrue(underTest.isScalable());
        assertEquals(0, underTest.getLeftPad());
        assertEquals(0, underTest.getMinWidth());
        assertEquals(TextStyle.UNSET_MAX_WIDTH, underTest.getMaxWidth());
    }
}
