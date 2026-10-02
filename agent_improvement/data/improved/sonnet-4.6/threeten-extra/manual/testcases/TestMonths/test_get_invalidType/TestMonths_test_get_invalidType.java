package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.IsoFields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMonths_test_get_invalidType {

    @Test
    @DisplayName("get() throws DateTimeException when queried with an unsupported unit (QUARTER_YEARS)")
    public void test_get_invalidType() {
        assertThrows(DateTimeException.class, () -> Months.of(6).get(IsoFields.QUARTER_YEARS));
    }
}
