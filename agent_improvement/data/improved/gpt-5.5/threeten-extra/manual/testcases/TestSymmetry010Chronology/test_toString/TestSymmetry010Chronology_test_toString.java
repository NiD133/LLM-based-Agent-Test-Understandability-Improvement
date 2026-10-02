package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSymmetry010Chronology_test_toString {

    public static Object[][] data_toString() {
        return new Object[][] {
                { Symmetry010Date.of(1, 1, 1), "Sym010 CE 1/01/01" },
                { Symmetry010Date.of(1970, 2, 31), "Sym010 CE 1970/02/31" },
                { Symmetry010Date.of(2000, 8, 31), "Sym010 CE 2000/08/31" },
                { Symmetry010Date.of(2009, 12, 37), "Sym010 CE 2009/12/37" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(Symmetry010Date date, String expected) {
        assertEquals(expected, date.toString());
    }
}
