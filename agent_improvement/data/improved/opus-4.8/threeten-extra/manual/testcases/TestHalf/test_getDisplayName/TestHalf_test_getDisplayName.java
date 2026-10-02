package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#getDisplayName(TextStyle, Locale)}.
 */
public class TestHalf_test_getDisplayName {

    @Test
    public void getDisplayName_forFirstHalf_containsHalfNumber() {
        // The short, US-locale display name for the first half-of-year should mention its number "1".
        String displayName = Half.H1.getDisplayName(TextStyle.SHORT, Locale.US);

        assertTrue(displayName.contains("1"));
    }
}
