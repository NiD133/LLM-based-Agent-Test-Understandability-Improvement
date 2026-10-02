package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#getDisplayName(java.time.format.TextStyle, Locale)}
 * rejects a null {@code TextStyle} argument.
 */
public class TestQuarter_test_getDisplayName_nullStyle {

    @Test
    public void getDisplayName_withNullStyle_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> Quarter.Q1.getDisplayName(null, Locale.US));
    }
}
