package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_compareTo_null {

    /**
     * Days.compareTo(null) must throw NullPointerException as required by
     * the Comparable contract (see Comparable.compareTo Javadoc).
     */
    @Test
    public void test_compareTo_null() {
        Days test5 = Days.of(5);
        assertThrows(NullPointerException.class, () -> test5.compareTo(null));
    }
}
