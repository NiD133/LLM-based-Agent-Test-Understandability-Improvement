package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        assertEquals(4, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 4));
        assertEquals(3, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 3));
        assertEquals(2, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 2));
        assertEquals(1, PaxChronology.INSTANCE.prolepticYear(PaxEra.CE, 1));

        assertEquals(0, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 1));
        assertEquals(-1, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 2));
        assertEquals(-2, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 3));
        assertEquals(-3, PaxChronology.INSTANCE.prolepticYear(PaxEra.BCE, 4));
    }
}
