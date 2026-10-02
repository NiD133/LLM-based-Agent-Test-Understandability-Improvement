package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#eraOf(int)} rejects an era value that does
 * not correspond to any Julian era. The Julian calendar defines only two eras,
 * BC (0) and AD (1), so a value of 2 is out of range.
 */
public class TestJulianChronology_test_Chronology_eraOf_invalid {

    @Test
    public void eraOf_withUnknownEraValue_throwsDateTimeException() {
        int unknownEraValue = 2;

        assertThrows(DateTimeException.class,
                () -> JulianChronology.INSTANCE.eraOf(unknownEraValue));
    }
}
