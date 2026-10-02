package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

public class TestYears_test_from_decadesAndMonths {

    @Test
    public void test_from_decadesAndMonths() {
        TemporalAmount twoDecadesMinusTwelveMonths = new MockDecadesMonths(2, -12);

        Years actual = Years.from(twoDecadesMinusTwelveMonths);

        assertEquals(Years.of(19), actual);
    }
}
