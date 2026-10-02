package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertProlepticYear(4, 4);
        assertProlepticYear(3, 3);
        assertProlepticYear(2, 2);
        assertProlepticYear(1, 1);
        assertProlepticYear(2000, 2000);
        assertProlepticYear(1582, 1582);
    }

    private static void assertProlepticYear(int expectedProlepticYear, int yearOfEra) {
        assertEquals(
                expectedProlepticYear,
                InternationalFixedChronology.INSTANCE.prolepticYear(InternationalFixedEra.CE, yearOfEra));
    }
}
