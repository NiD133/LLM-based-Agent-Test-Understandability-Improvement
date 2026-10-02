package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#getDisplayName(TextStyle, Locale)}.
 */
public class TestQuarter_test_getDisplayName {

    @Test
    public void test_getDisplayName() {
        // Q1 rendered in the SHORT style for the US locale is the text "Q1".
        String displayName = Quarter.Q1.getDisplayName(TextStyle.SHORT, Locale.US);

        assertEquals("Q1", displayName);
    }
}
