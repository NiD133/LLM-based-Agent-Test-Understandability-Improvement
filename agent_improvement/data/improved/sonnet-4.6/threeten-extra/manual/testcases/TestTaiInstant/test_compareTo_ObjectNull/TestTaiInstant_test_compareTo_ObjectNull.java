package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_compareTo_ObjectNull {

    @Test
    public void test_compareTo_ObjectNull() {
        TaiInstant a = TaiInstant.ofTaiSeconds(0L, 0);
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> a.compareTo(null));
    }
}
