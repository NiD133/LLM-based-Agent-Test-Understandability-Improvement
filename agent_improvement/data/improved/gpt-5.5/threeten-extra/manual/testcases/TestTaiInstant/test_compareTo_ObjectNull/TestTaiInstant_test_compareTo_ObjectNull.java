package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_compareTo_ObjectNull {

    @Test
    public void test_compareTo_ObjectNull() {
        TaiInstant instant = TaiInstant.ofTaiSeconds(0L, 0);

        //noinspection DataFlowIssue - testing null handling
        assertThrows(NullPointerException.class, () -> instant.compareTo(null));
    }
}
