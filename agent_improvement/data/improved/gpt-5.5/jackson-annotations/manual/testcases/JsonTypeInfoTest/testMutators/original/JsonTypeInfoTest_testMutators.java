package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testMutators extends AnnotationTestUtil {

    @Test
    public void testMutators() throws Exception {
        JsonTypeInfo.Value v = JsonTypeInfo.Value.from(Anno1.class.getAnnotation(JsonTypeInfo.class));
        assertEquals(JsonTypeInfo.Id.CLASS, v.getIdType());
        assertSame(v, v.withIdType(JsonTypeInfo.Id.CLASS));
        JsonTypeInfo.Value v2 = v.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, v2.getIdType());
        JsonTypeInfo.Value v3 = v.withIdType(JsonTypeInfo.Id.SIMPLE_NAME);
        assertEquals(JsonTypeInfo.Id.SIMPLE_NAME, v3.getIdType());
        assertEquals(JsonTypeInfo.As.PROPERTY, v.getInclusionType());
        assertSame(v, v.withInclusionType(JsonTypeInfo.As.PROPERTY));
        v2 = v.withInclusionType(JsonTypeInfo.As.EXTERNAL_PROPERTY);
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, v2.getInclusionType());
        assertSame(v, v.withDefaultImpl(null));
        v2 = v.withDefaultImpl(String.class);
        assertEquals(String.class, v2.getDefaultImpl());
        assertSame(v, v.withIdVisible(true));
        assertFalse(v.withIdVisible(false).getIdVisible());
        assertEquals("foobar", v.withPropertyName("foobar").getPropertyName());
    }
}
