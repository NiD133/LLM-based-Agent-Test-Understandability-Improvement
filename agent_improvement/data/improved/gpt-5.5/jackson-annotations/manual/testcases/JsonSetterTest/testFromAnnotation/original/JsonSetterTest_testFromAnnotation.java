package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testFromAnnotation() throws Exception {
        // legal
        assertSame(EMPTY, JsonSetter.Value.from(null));
        JsonSetter ann = Bogus.class.getField("field").getAnnotation(JsonSetter.class);
        JsonSetter.Value v = JsonSetter.Value.from(ann);
        assertEquals(Nulls.FAIL, v.getValueNulls());
        assertEquals(Nulls.SKIP, v.getContentNulls());
        // Let's also verify JDK serializability
        byte[] b = jdkSerialize(v);
        JsonSetter.Value deser = jdkDeserialize(b);
        assertEquals(v, deser);
    }
}
