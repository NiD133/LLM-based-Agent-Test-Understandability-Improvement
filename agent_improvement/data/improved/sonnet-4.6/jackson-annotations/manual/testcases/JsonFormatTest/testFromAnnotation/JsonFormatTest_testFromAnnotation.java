package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFromAnnotation extends AnnotationTestUtil {

    // Sentinel representing an unconfigured format: all fields at their defaults.
    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    // A fixture class whose @JsonFormat annotation we parse in the test below.
    // The timezone "bogus" is intentionally invalid so we can verify it is stored
    // as a raw string and not resolved/parsed eagerly.
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN, pattern = "xyz", timezone = "bogus")
    private static final class BogusAnnotationHolder { }

    @Test
    public void testFromAnnotation() {
        // Passing null should return the shared EMPTY singleton (not a new instance).
        assertSame(EMPTY, JsonFormat.Value.from(null));

        // Parse a real @JsonFormat annotation and verify each field is transferred correctly.
        JsonFormat ann = BogusAnnotationHolder.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value formatValue = JsonFormat.Value.from(ann);

        assertEquals("xyz", formatValue.getPattern());
        assertEquals(JsonFormat.Shape.BOOLEAN, formatValue.getShape());
        // Invalid timezone IDs must be preserved verbatim; timeZoneAsString() must not throw.
        assertEquals("bogus", formatValue.timeZoneAsString());

        // [annotations#316] JsonFormat.Value must survive a JDK serialization round-trip intact.
        byte[] serializedBytes = jdkSerialize(formatValue);
        JsonFormat.Value deserializedValue = jdkDeserialize(serializedBytes);
        assertEquals(formatValue, deserializedValue);
    }
}
