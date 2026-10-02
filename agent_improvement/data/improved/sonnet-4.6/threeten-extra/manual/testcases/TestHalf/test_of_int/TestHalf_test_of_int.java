package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_of_int {

    @Test
    public void test_of_int_validH1() {
        assertEquals(1, Half.of(1).getValue());
    }

    @Test
    public void test_of_int_validH2() {
        assertEquals(2, Half.of(2).getValue());
    }

    @Test
    public void test_of_int_invalidBelowRange() {
        assertThrows(DateTimeException.class, () -> Half.of(0));
    }

    @Test
    public void test_of_int_invalidAboveRange() {
        assertThrows(DateTimeException.class, () -> Half.of(3));
    }
}
