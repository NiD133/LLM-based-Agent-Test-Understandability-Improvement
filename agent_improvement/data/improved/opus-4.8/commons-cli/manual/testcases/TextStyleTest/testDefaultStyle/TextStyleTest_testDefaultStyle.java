package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the property values of the shared {@link TextStyle#DEFAULT} instance.
 */
public class TextStyleTest_testDefaultStyle {

    /**
     * {@link TextStyle#DEFAULT} is created from a fresh builder, so it must expose
     * the builder's documented default values: left alignment, scalable, no left
     * padding, no minimum width, and an unset maximum width.
     */
    @Test
    void testDefaultStyle() {
        final TextStyle defaultStyle = TextStyle.DEFAULT;

        assertEquals(TextStyle.Alignment.LEFT, defaultStyle.getAlignment(), "alignment");
        assertTrue(defaultStyle.isScalable(), "scalable");
        assertEquals(0, defaultStyle.getLeftPad(), "leftPad");
        assertEquals(0, defaultStyle.getMinWidth(), "minWidth");
        assertEquals(TextStyle.UNSET_MAX_WIDTH, defaultStyle.getMaxWidth(), "maxWidth");
    }
}
