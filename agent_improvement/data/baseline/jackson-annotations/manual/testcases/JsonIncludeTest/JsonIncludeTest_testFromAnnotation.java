package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    @Test
    public void testFromAnnotation() {
        JsonInclude ann = Bogus.class.getAnnotation(JsonInclude.class);
        JsonInclude.Value v = JsonInclude.Value.from(ann);
        assertEquals(Include.NON_EMPTY, v.getValueInclusion());
        assertEquals(Include.NON_DEFAULT, v.getContentInclusion());
        // Let's also verify JDK serializability
        byte[] b = jdkSerialize(v);
        JsonInclude.Value deser = jdkDeserialize(b);
        assertEquals(v, deser);
    }
}
