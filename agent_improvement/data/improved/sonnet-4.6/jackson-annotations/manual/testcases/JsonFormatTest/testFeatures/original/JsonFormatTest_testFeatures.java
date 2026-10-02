package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFeatures extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testFeatures() {
        JsonFormat.Features f1 = JsonFormat.Features.empty();
        JsonFormat.Features f2 = f1.with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY).without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        assertTrue(f1.equals(f1));
        assertFalse(f1.equals(f2));
        assertFalse(f1.equals(null));
        assertFalse(f1.equals("foo"));
        assertNull(f1.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, f2.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertNull(f1.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
        assertEquals(Boolean.FALSE, f2.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
        JsonFormat.Features f3 = f1.withOverrides(f2);
        assertEquals(Boolean.TRUE, f3.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.FALSE, f3.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
        // switch values around
        JsonFormat.Features f4 = JsonFormat.Features.construct(new Feature[] { // enabled:
        Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS }, new Feature[] { // enabled
        Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY });
        assertEquals(Boolean.FALSE, f4.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, f4.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }
}
