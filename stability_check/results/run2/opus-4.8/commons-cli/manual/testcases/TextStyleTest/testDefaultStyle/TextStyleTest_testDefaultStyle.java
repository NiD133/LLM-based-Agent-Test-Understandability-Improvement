package org.apache.commons.cli.help;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the property values of the shared {@link TextStyle#DEFAULT} instance.
 */
public class TextStyleTest_testDefaultStyle {

    /**
     * The {@link TextStyle#DEFAULT} instance is produced by an unmodified builder,
     * so it must expose the builder's documented default values:
     * left alignment, scalable, no left padding, no minimum width and an unset
     * maximum width.
     */
    @Test
    void testDefaultStyle() {
        final TextStyle defaultStyle = TextStyle.DEFAULT;

        assertEquals(TextStyle.Alignment.LEFT, defaultStyle.getAlignment(), "default alignment should be LEFT");
        assertTrue(defaultStyle.isScalable(), "default style should be scalable");
        assertEquals(0, defaultStyle.getLeftPad(), "default left padding should be 0");
        assertEquals(0, defaultStyle.getMinWidth(), "default minimum width should be 0");
        assertEquals(TextStyle.UNSET_MAX_WIDTH, defaultStyle.getMaxWidth(), "default maximum width should be unset");
    }
}
