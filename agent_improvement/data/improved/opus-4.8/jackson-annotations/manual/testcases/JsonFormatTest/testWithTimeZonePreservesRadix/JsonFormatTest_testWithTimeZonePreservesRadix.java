package com.fasterxml.jackson.annotation;

import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that applying a time zone to a {@link JsonFormat.Value} via
 * {@link JsonFormat.Value#withTimeZone} keeps the previously configured radix intact.
 */
public class JsonFormatTest_testWithTimeZonePreservesRadix extends AnnotationTestUtil {

    @Test
    void withTimeZoneShouldPreserveRadix() {
        int binaryRadix = 2;

        // Start from a value that only specifies a (binary) radix...
        JsonFormat.Value valueWithRadix = JsonFormat.Value.forRadix(binaryRadix);

        // ...then attach a time zone, which must not disturb the radix.
        JsonFormat.Value valueWithTimeZone =
                valueWithRadix.withTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals(binaryRadix, valueWithTimeZone.getRadix(),
                "Radix should remain unchanged after setting the time zone");
    }
}
