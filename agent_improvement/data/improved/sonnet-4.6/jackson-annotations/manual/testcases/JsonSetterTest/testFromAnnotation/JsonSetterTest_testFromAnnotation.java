package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testFromAnnotation extends AnnotationTestUtil {

    // A helper class whose annotated field is used to test Value.from(JsonSetter)
    private static final class Bogus {
        @JsonSetter(nulls = Nulls.FAIL, contentNulls = Nulls.SKIP)
        public int field;
    }

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testFromAnnotation() throws Exception {
        // Passing null must return the shared EMPTY singleton
        assertSame(EMPTY, JsonSetter.Value.from(null));

        // Build a Value from a real @JsonSetter annotation read off Bogus.field
        JsonSetter annotation = Bogus.class.getField("field").getAnnotation(JsonSetter.class);
        JsonSetter.Value value = JsonSetter.Value.from(annotation);

        // The annotation declared nulls=FAIL and contentNulls=SKIP; verify both are preserved
        assertEquals(Nulls.FAIL, value.getValueNulls());
        assertEquals(Nulls.SKIP, value.getContentNulls());

        // Round-trip through Java serialization and confirm equality is maintained
        byte[] serialized = jdkSerialize(value);
        JsonSetter.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }
}
