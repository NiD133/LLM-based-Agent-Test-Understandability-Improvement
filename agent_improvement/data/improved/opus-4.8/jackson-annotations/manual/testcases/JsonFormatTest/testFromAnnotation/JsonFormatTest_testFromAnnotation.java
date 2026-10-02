package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies {@link JsonFormat.Value#from(JsonFormat)}: building a
 * {@code JsonFormat.Value} from an actual {@code @JsonFormat} annotation
 * (and from {@code null}), and that the result survives JDK serialization.
 */
public class JsonFormatTest_testFromAnnotation extends AnnotationTestUtil {

    /**
     * Sample type carrying a {@code @JsonFormat} annotation to read from.
     * Note: {@code timezone="bogus"} is intentionally not a valid time zone id,
     * so the test only checks the raw string is carried through unchanged.
     */
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN, pattern = "xyz", timezone = "bogus")
    private static final class AnnotatedSample { }

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testFromAnnotation() {
        // A null annotation maps to the shared empty value (same instance).
        assertSame(EMPTY, JsonFormat.Value.from(null));

        // A real annotation: each field is copied into the Value.
        JsonFormat annotation = AnnotatedSample.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value value = JsonFormat.Value.from(annotation);

        assertEquals("xyz", value.getPattern());
        assertEquals(JsonFormat.Shape.BOOLEAN, value.getShape());
        // Time zone string is carried verbatim; "bogus" is not resolved to a real TimeZone.
        assertEquals("bogus", value.timeZoneAsString());

        // [annotations#316]: the Value must remain equal after JDK round-trip serialization.
        byte[] serialized = jdkSerialize(value);
        JsonFormat.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }
}
