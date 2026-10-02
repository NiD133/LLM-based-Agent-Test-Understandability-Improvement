package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TestHalf_test_getDisplayName_nullStyle {

    @Test
    public void test_getDisplayName_nullStyle() {
        // getDisplayName requires a non-null TextStyle; null must throw NullPointerException
        assertThrows(NullPointerException.class, () -> Half.H1.getDisplayName(null, Locale.US));
    }
}
