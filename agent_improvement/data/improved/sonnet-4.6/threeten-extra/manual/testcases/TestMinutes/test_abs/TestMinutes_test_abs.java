package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_abs {

    @Test
    public void test_abs() {
        // zero stays zero
        assertEquals(Minutes.of(0), Minutes.of(0).abs());
        // positive value is unchanged
        assertEquals(Minutes.of(12), Minutes.of(12).abs());
        // negative value becomes its positive counterpart
        assertEquals(Minutes.of(12), Minutes.of(-12).abs());
        // boundary: Integer.MAX_VALUE is already positive, unchanged
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE).abs());
        // boundary: negated Integer.MAX_VALUE becomes Integer.MAX_VALUE
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(-Integer.MAX_VALUE).abs());
    }
}
