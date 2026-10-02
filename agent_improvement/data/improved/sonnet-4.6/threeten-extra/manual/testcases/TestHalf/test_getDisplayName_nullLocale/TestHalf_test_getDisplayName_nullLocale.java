package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.TextStyle;

import org.junit.jupiter.api.Test;

public class TestHalf_test_getDisplayName_nullLocale {

    @Test
    public void test_getDisplayName_nullLocale() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> Half.H1.getDisplayName(TextStyle.FULL, null));
    }
}
