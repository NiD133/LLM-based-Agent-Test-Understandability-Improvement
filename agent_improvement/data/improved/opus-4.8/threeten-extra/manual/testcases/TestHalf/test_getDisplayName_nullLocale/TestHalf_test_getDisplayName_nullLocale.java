package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#getDisplayName(TextStyle, java.util.Locale)} rejects a null locale.
 */
public class TestHalf_test_getDisplayName_nullLocale {

    @Test
    public void getDisplayName_withNullLocale_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> Half.H1.getDisplayName(TextStyle.FULL, null));
    }
}
