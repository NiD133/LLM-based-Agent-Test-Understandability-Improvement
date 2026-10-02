package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#getDisplayName} rejects a null text style.
 */
public class TestHalf_test_getDisplayName_nullStyle {

    @Test
    public void getDisplayName_withNullStyle_throwsNullPointerException() {
        TextStyle nullStyle = null;
        assertThrows(NullPointerException.class,
                () -> Half.H1.getDisplayName(nullStyle, Locale.US));
    }
}
