package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIncludeTest_testFromAnnotationWithCustom extends AnnotationTestUtil {

    private final JsonInclude.Value EMPTY = JsonInclude.Value.empty();

    @Test
    public void testFromAnnotationWithCustom() {
        JsonInclude ann = Custom.class.getAnnotation(JsonInclude.class);
        JsonInclude.Value v = JsonInclude.Value.from(ann);
        assertEquals(Include.CUSTOM, v.getValueInclusion());
        assertEquals(Include.CUSTOM, v.getContentInclusion());
        assertEquals(Integer.class, v.getValueFilter());
        assertEquals(Long.class, v.getContentFilter());
        assertEquals("JsonInclude.Value(value=CUSTOM,content=CUSTOM,valueFilter=java.lang.Integer.class,contentFilter=java.lang.Long.class)", v.toString());
    }
}
