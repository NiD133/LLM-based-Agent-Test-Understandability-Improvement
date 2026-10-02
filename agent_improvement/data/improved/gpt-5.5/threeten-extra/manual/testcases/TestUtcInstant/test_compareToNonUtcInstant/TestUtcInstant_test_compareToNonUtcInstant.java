package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_compareToNonUtcInstant {

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void test_compareToNonUtcInstant() {
        Comparable utcInstant = UtcInstant.ofModifiedJulianDay(0L, 2);
        Object nonUtcInstant = new Object();

        assertThrows(ClassCastException.class, () -> utcInstant.compareTo(nonUtcInstant));
    }
}
