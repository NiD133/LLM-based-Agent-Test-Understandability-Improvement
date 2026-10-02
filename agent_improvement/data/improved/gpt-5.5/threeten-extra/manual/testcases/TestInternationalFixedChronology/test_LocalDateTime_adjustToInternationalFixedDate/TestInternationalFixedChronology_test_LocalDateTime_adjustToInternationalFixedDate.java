package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_LocalDateTime_adjustToInternationalFixedDate {

    @Test
    public void test_LocalDateTime_adjustToInternationalFixedDate() {
        InternationalFixedDate fixedDate = InternationalFixedDate.of(2012, 7, 19);

        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(fixedDate);

        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjustedDateTime);
    }
}
