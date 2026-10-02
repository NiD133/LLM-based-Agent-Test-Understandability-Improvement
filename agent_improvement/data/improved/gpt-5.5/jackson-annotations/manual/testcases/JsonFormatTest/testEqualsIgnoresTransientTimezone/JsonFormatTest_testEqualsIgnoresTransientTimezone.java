package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testEqualsIgnoresTransientTimezone extends AnnotationTestUtil {

    private static final String EMPTY_PATTERN = "";
    private static final Shape ANY_SHAPE = Shape.ANY;
    private static final String EMPTY_LOCALE = "";
    private static final String UTC_TIMEZONE_ID = "UTC";
    private static final JsonFormat.Features NO_FEATURE_OVERRIDES = JsonFormat.Features.empty();

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testEqualsIgnoresTransientTimezone() {
        JsonFormat.Value valueWithResolvedTimezone = new JsonFormat.Value(
                EMPTY_PATTERN,
                ANY_SHAPE,
                EMPTY_LOCALE,
                UTC_TIMEZONE_ID,
                NO_FEATURE_OVERRIDES,
                null,
                DEFAULT_RADIX);
        JsonFormat.Value valueWithUnresolvedTimezone = new JsonFormat.Value(
                EMPTY_PATTERN,
                ANY_SHAPE,
                EMPTY_LOCALE,
                UTC_TIMEZONE_ID,
                NO_FEATURE_OVERRIDES,
                null,
                DEFAULT_RADIX);

        valueWithResolvedTimezone.getTimeZone();

        assertEquals(valueWithResolvedTimezone, valueWithUnresolvedTimezone);
    }
}
