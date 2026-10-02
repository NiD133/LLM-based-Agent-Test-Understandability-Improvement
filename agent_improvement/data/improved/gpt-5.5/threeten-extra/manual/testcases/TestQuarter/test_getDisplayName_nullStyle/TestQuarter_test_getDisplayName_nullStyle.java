package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getDisplayName_nullStyle {

    @Test
    public void test_getDisplayName_nullStyle() {
        assertThrows(
                NullPointerException.class,
                () -> Quarter.Q1.getDisplayName(null, Locale.US));
    }
}
