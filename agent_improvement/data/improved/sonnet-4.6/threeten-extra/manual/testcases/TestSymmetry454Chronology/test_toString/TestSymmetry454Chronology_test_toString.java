package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_toString {

    public static Object[][] data_toString() {
        return new Object[][] {
            { Symmetry454Date.of(1, 1, 1),       "Sym454 CE 1/01/01"       },
            { Symmetry454Date.of(1970, 2, 35),   "Sym454 CE 1970/02/35"    },
            { Symmetry454Date.of(2000, 8, 35),   "Sym454 CE 2000/08/35"    },
            { Symmetry454Date.of(1970, 12, 35),  "Sym454 CE 1970/12/35"    },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(Symmetry454Date date, String expected) {
        assertEquals(expected, date.toString());
    }
}
