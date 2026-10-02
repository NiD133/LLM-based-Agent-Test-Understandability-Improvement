package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant} rejects comparisons against incompatible types.
 */
public class TestTaiInstant_test_compareToNonTaiInstant {

    /**
     * {@code TaiInstant} implements {@code Comparable<TaiInstant>}, so comparing it
     * to an arbitrary {@code Object} via the raw {@code Comparable} interface must
     * fail with a {@link ClassCastException}.
     */
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void test_compareToNonTaiInstant() {
        Comparable taiInstant = TaiInstant.ofTaiSeconds(0L, 2);

        assertThrows(ClassCastException.class, () -> taiInstant.compareTo(new Object()));
    }
}
