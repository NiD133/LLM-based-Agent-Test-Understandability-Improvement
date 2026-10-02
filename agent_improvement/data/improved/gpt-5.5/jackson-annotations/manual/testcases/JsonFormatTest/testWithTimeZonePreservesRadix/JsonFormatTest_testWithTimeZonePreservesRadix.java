package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest_testWithTimeZonePreservesRadix extends AnnotationTestUtil {

    private static final int BINARY_RADIX = 2;
    private static final String UTC_TIME_ZONE_ID = "UTC";

    private final JsonFormat.Value emptyValue = JsonFormat.Value.empty();

    @Test
    void testWithTimeZonePreservesRadix() {
        JsonFormat.Value valueWithBinaryRadix = JsonFormat.Value.forRadix(BINARY_RADIX);

        JsonFormat.Value valueWithUtcTimeZone = valueWithBinaryRadix.withTimeZone(
                TimeZone.getTimeZone(UTC_TIME_ZONE_ID));

        assertEquals(BINARY_RADIX, valueWithUtcTimeZone.getRadix());
    }
}
