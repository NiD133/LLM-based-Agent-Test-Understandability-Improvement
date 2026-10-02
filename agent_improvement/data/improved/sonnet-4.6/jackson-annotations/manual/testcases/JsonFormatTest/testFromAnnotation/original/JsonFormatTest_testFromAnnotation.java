package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testFromAnnotation() {
        // Trivial case first:
        assertSame(EMPTY, JsonFormat.Value.from(null));
        // then real one
        JsonFormat ann = Bogus.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value v = JsonFormat.Value.from(ann);
        assertEquals("xyz", v.getPattern());
        assertEquals(JsonFormat.Shape.BOOLEAN, v.getShape());
        // note: since it's not valid, should not try access as real thing
        assertEquals("bogus", v.timeZoneAsString());
        // [annotations#316]: let's also verify JDK serializability
        byte[] b = jdkSerialize(v);
        JsonFormat.Value v2 = jdkDeserialize(b);
        assertEquals(v, v2);
    }
}
