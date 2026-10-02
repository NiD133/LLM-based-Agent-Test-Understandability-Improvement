package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_compareTo_null {

    /**
     * Seconds.compareTo() must throw NullPointerException when passed null,
     * as required by the Comparable contract (Comparable.compareTo javadoc).
     */
    @Test
    public void test_compareTo_null() {
        Seconds fiveSeconds = Seconds.of(5);
        assertThrows(NullPointerException.class, () -> fiveSeconds.compareTo(null));
    }
}
