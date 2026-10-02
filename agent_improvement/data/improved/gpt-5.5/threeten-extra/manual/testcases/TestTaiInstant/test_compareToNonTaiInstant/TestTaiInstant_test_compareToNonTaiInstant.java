package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_compareToNonTaiInstant {

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void test_compareToNonTaiInstant() {
        Comparable taiInstant = TaiInstant.ofTaiSeconds(0L, 2);

        assertThrows(ClassCastException.class, () -> taiInstant.compareTo(new Object()));
    }
}
