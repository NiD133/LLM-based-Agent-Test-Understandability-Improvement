package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getDisplayName_nullLocale {

    @Test
    public void test_getDisplayName_nullLocale() {
        assertThrows(
                NullPointerException.class,
                () -> Quarter.Q1.getDisplayName(TextStyle.FULL, null));
    }
}
