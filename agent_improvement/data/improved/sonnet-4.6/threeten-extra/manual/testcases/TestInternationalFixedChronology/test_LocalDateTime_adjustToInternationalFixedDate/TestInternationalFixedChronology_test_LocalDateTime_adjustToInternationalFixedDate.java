package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDateTime_adjustToInternationalFixedDate {

    @Test
    public void test_LocalDateTime_adjustToInternationalFixedDate() {
        // IFC 2012/07/19 corresponds to ISO 2012-07-06
        InternationalFixedDate ifcDate = InternationalFixedDate.of(2012, 7, 19);
        LocalDateTime adjusted = LocalDateTime.MIN.with(ifcDate);
        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjusted);
    }
}
