package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#getDisplayName(java.time.format.TextStyle, Locale)}
 * rejects a null {@code style} argument.
 */
public class TestAmPm_test_getDisplayName_nullStyle {

    @Test
    public void getDisplayName_withNullStyle_throwsNullPointerException() {
        // The style argument is documented as "not null"; passing null must be rejected.
        assertThrows(NullPointerException.class, () -> AmPm.AM.getDisplayName(null, Locale.US));
    }
}
