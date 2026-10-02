package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_getDisplayName {

    @Test
    public void test_getDisplayName() {
        assertEquals("AM", AmPm.AM.getDisplayName(TextStyle.SHORT, Locale.US));
    }
}
