package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_compareToNonUtcInstant {

    /**
     * UtcInstant implements Comparable<UtcInstant>, so comparing it to a plain
     * Object via the raw Comparable type must throw ClassCastException at runtime.
     */
    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void test_compareToNonUtcInstant() {
        Comparable utcInstantAsRawComparable = UtcInstant.ofModifiedJulianDay(0L, 2);
        assertThrows(ClassCastException.class, () -> utcInstantAsRawComparable.compareTo(new Object()));
    }
}
