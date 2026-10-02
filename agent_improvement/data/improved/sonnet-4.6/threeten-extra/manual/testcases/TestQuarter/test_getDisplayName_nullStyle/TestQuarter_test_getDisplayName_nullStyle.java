package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getDisplayName_nullStyle {

    @Test
    public void test_getDisplayName_nullStyle() {
        // getDisplayName requires a non-null TextStyle; passing null must throw NullPointerException
        assertThrows(NullPointerException.class, () -> Quarter.Q1.getDisplayName((TextStyle) null, Locale.US));
    }
}
