package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#from(java.time.temporal.TemporalAccessor)}.
 *
 * <p>{@code Quarter.from} derives the quarter-of-year from any ISO temporal object,
 * mapping each month to its quarter: Jan-Mar to Q1, Apr-Jun to Q2,
 * Jul-Sep to Q3 and Oct-Dec to Q4.
 */
public class TestQuarter_test_from_TemporalAccessor {

    @Test
    public void from_LocalDate_inJune_returnsQ2() {
        LocalDate dateInJune = LocalDate.of(2011, 6, 6);

        assertEquals(Quarter.Q2, Quarter.from(dateInJune));
    }

    @Test
    public void from_LocalDateTime_inFebruary_returnsQ1() {
        LocalDateTime dateTimeInFebruary = LocalDateTime.of(2012, 2, 3, 12, 30);

        assertEquals(Quarter.Q1, Quarter.from(dateTimeInFebruary));
    }
}
