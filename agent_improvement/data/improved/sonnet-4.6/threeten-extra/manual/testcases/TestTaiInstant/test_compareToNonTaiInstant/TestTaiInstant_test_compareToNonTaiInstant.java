package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_compareToNonTaiInstant {

    /**
     * Verifies that comparing a TaiInstant against a non-TaiInstant object via
     * the raw Comparable interface throws ClassCastException, consistent with
     * TaiInstant implementing Comparable<TaiInstant> (not Comparable<Object>).
     */
    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void test_compareToNonTaiInstant() {
        Comparable c = TaiInstant.ofTaiSeconds(0L, 2);
        assertThrows(ClassCastException.class, () -> c.compareTo(new Object()));
    }
}
