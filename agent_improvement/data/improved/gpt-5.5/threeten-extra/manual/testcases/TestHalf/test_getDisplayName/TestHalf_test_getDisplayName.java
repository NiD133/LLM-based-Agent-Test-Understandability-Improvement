package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TestHalf_test_getDisplayName {

    @Test
    public void test_getDisplayName() {
        assertTrue(Half.H1.getDisplayName(TextStyle.SHORT, Locale.US).contains("1"));
    }
}
