package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonFormat.Value#equals} is based on the timezone string (_timezoneStr),
 * not on the transient cached {@link java.util.TimeZone} object that is lazily created by
 * {@link JsonFormat.Value#getTimeZone()}. Two values carrying the same timezone string must
 * compare equal whether or not the lazy field has been populated.
 */
public class JsonFormatTest_testEqualsIgnoresTransientTimezone extends AnnotationTestUtil {

    // Shared constructor arguments that are identical for both values under test.
    private static final String PATTERN = "";
    private static final String LOCALE  = "";
    private static final String TIMEZONE = "UTC";

    /**
     * Creates a {@link JsonFormat.Value} configured with {@value #TIMEZONE} as the timezone
     * string, using otherwise empty/default settings.
     */
    private static JsonFormat.Value utcValue() {
        return new JsonFormat.Value(
                PATTERN,
                Shape.ANY,
                LOCALE,
                TIMEZONE,
                JsonFormat.Features.empty(),
                /*lenient=*/ null,
                DEFAULT_RADIX
        );
    }

    @Test
    void testEqualsIgnoresTransientTimezone() {
        // Both values are constructed identically; their _timezoneStr is "UTC" and the
        // transient _timezone field starts as null in each.
        JsonFormat.Value withUnpopulatedTimezone = utcValue();
        JsonFormat.Value withPopulatedTimezone   = utcValue();

        // Calling getTimeZone() on one value triggers the lazy initialisation of its
        // internal transient TimeZone field, while the other value's field stays null.
        withPopulatedTimezone.getTimeZone();

        // equals() must ignore the transient field and compare only _timezoneStr,
        // so the two values remain equal.
        assertEquals(withPopulatedTimezone, withUnpopulatedTimezone,
                "Values with the same timezone string must be equal regardless of "
                + "whether the transient TimeZone cache has been populated.");
    }
}
