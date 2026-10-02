package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestYears_test_addTo {

    @Test
    public void addTo_returnsOriginalDateForZeroYearsAndAdvancedDateForPositiveYears() {
        assertEquals(LocalDate.of(2019, 1, 10), Years.of(0).addTo(LocalDate.of(2019, 1, 10)));
        assertEquals(LocalDate.of(2024, 1, 10), Years.of(5).addTo(LocalDate.of(2019, 1, 10)));
    }
}
