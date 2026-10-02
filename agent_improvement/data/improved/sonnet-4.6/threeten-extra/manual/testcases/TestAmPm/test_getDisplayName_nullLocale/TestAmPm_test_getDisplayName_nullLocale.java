package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_getDisplayName_nullLocale {

    /**
     * getDisplayName requires a non-null Locale; passing null must throw NullPointerException
     * because the underlying DateTimeFormatter.toFormatter(null) rejects it.
     */
    @Test
    public void test_getDisplayName_nullLocale() {
        assertThrows(NullPointerException.class, () -> AmPm.AM.getDisplayName(TextStyle.FULL, null));
    }
}
