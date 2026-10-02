package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getDisplayName {

    @Test
    public void test_getDisplayName() {
        assertEquals("Q1", Quarter.Q1.getDisplayName(TextStyle.SHORT, Locale.US));
    }
}
