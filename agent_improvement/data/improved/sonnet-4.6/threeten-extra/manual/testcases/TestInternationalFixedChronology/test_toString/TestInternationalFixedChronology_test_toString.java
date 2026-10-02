package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_toString {

    public static Object[][] data_toString() {
        return new Object[][] {
            { InternationalFixedDate.of(1,    1,  1),  "Ifc CE 1/01/01"    },
            { InternationalFixedDate.of(2012, 6,  23), "Ifc CE 2012/06/23" },
            { InternationalFixedDate.of(1,    13, 29), "Ifc CE 1/13/29"    },
            { InternationalFixedDate.of(2012, 6,  29), "Ifc CE 2012/06/29" },
            { InternationalFixedDate.of(2012, 13, 29), "Ifc CE 2012/13/29" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(InternationalFixedDate date, String expected) {
        assertEquals(expected, date.toString());
    }
}
