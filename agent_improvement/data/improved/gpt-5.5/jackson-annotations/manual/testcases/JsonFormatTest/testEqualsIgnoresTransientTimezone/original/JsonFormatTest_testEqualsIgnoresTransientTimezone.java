package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testEqualsIgnoresTransientTimezone extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    void testEqualsIgnoresTransientTimezone() {
        // Two Values created from same timezone string should be equal
        // regardless of whether getTimeZone() has been called
        JsonFormat.Value v1 = new JsonFormat.Value("", Shape.ANY, "", "UTC", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        JsonFormat.Value v2 = new JsonFormat.Value("", Shape.ANY, "", "UTC", JsonFormat.Features.empty(), null, DEFAULT_RADIX);
        // Force lazy _timezone population on v1 only
        v1.getTimeZone();
        assertEquals(v1, v2);
    }
}
