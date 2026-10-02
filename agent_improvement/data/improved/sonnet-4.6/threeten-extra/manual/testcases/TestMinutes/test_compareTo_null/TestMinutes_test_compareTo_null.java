package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#compareTo(Minutes)} throws NullPointerException
 * when passed a null argument, as required by the Comparable contract.
 */
public class TestMinutes_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Minutes minutes = Minutes.of(5);
        assertThrows(NullPointerException.class, () -> minutes.compareTo(null));
    }
}
