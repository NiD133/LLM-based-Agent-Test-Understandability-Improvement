package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> actualEras = PaxChronology.INSTANCE.eras();

        assertEquals(2, actualEras.size());
        assertTrue(actualEras.contains(PaxEra.BCE));
        assertTrue(actualEras.contains(PaxEra.CE));
    }
}
