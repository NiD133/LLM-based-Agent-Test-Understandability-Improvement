package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#getDisplayName(TextStyle, Locale)}.
 */
public class TestAmPm_test_getDisplayName {

    @Test
    public void test_getDisplayName_shortStyle_usLocale_returnsAM() {
        // The SHORT text style for AM in the US locale is the literal "AM".
        String displayName = AmPm.AM.getDisplayName(TextStyle.SHORT, Locale.US);

        assertEquals("AM", displayName);
    }
}
