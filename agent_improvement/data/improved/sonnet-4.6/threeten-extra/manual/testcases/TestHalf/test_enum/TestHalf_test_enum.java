package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHalf_test_enum {

    @Test
    public void test_enum_valueOf_returnsCorrectConstant() {
        assertEquals(Half.H2, Half.valueOf("H2"));
    }

    @Test
    public void test_enum_values_firstElementIsH1() {
        assertEquals(Half.H1, Half.values()[0]);
    }
}
