package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Seconds fiveSeconds = Seconds.of(5);

        // Intentionally pass null to document Comparable's required failure mode.
        assertThrows(NullPointerException.class, () -> fiveSeconds.compareTo(null));
    }
}
