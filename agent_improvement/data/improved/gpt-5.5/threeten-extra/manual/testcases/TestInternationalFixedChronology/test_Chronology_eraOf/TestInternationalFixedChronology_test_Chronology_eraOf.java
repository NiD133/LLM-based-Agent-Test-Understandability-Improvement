package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(InternationalFixedEra.CE, InternationalFixedChronology.INSTANCE.eraOf(1));
    }
}
