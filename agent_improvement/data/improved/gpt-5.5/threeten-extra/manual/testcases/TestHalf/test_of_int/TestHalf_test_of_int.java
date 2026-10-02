package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_of_int {

    @Test
    public void test_of_int() {
        assertEquals(1, Half.of(1).getValue());
        assertEquals(2, Half.of(2).getValue());
        assertThrows(DateTimeException.class, () -> Half.of(0));
        assertThrows(DateTimeException.class, () -> Half.of(3));
    }
}
