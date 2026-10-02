package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_eraOf_invalid {

    // Valid era values are 0 (BCE) and 1 (CE); any other value must throw DateTimeException.
    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> Symmetry010Chronology.INSTANCE.eraOf(2));
    }
}
