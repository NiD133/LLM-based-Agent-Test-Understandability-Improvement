package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link JsonFormat.Value#equals(Object)} ignores the lazily
 * resolved, {@code transient} {@code TimeZone} field.
 *
 * <p>A {@code Value} stores its time zone as a string ("UTC") and only converts
 * it into a {@link java.util.TimeZone} instance the first time
 * {@link JsonFormat.Value#getTimeZone()} is called. This test ensures that
 * triggering that lazy conversion on one instance does not make it unequal to an
 * otherwise identical instance that never resolved its time zone.
 */
public class JsonFormatTest_testEqualsIgnoresTransientTimezone extends AnnotationTestUtil {

    private static final String TIMEZONE_UTC = "UTC";

    /** Builds a {@code Value} carrying the given time zone string and only default settings otherwise. */
    private static JsonFormat.Value valueWithTimeZone(String timezone) {
        return new JsonFormat.Value(
                "",                          // pattern
                Shape.ANY,                   // shape
                "",                          // locale
                timezone,                    // timezone (stored as string, resolved lazily)
                JsonFormat.Features.empty(), // features
                null,                        // lenient
                DEFAULT_RADIX);              // radix
    }

    @Test
    void testEqualsIgnoresTransientTimezone() {
        JsonFormat.Value withResolvedTimeZone = valueWithTimeZone(TIMEZONE_UTC);
        JsonFormat.Value withUnresolvedTimeZone = valueWithTimeZone(TIMEZONE_UTC);

        // Force the lazy, transient _timezone field to be populated on one instance only.
        withResolvedTimeZone.getTimeZone();

        // Equality must depend on the time zone string, not on the resolved transient field.
        assertEquals(withResolvedTimeZone, withUnresolvedTimeZone);
    }
}
