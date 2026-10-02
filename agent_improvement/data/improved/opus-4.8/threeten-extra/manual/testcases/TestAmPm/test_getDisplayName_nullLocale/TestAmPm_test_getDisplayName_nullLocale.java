package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#getDisplayName(TextStyle, Locale)} rejects a null locale.
 */
public class TestAmPm_test_getDisplayName_nullLocale {

    @Test
    public void getDisplayName_withNullLocale_throwsNullPointerException() {
        Locale nullLocale = null;

        assertThrows(
                NullPointerException.class,
                () -> AmPm.AM.getDisplayName(TextStyle.FULL, nullLocale));
    }
}
