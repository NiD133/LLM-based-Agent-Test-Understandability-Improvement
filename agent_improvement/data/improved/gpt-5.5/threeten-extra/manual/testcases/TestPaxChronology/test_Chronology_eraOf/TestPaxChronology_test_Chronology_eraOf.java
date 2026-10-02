package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(PaxEra.CE, PaxChronology.INSTANCE.eraOf(1));
        assertEquals(PaxEra.BCE, PaxChronology.INSTANCE.eraOf(0));
    }
}
