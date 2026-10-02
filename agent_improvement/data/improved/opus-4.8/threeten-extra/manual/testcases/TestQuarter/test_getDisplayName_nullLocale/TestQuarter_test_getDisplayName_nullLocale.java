package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#getDisplayName(TextStyle, java.util.Locale)}
 * rejects a {@code null} locale.
 */
public class TestQuarter_test_getDisplayName_nullLocale {

    @Test
    public void getDisplayName_withNullLocale_throwsNullPointerException() {
        // The locale argument is required; passing null must fail fast.
        assertThrows(
                NullPointerException.class,
                () -> Quarter.Q1.getDisplayName(TextStyle.FULL, null));
    }
}
