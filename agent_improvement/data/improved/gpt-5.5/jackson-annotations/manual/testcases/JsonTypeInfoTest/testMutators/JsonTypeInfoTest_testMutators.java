package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonTypeInfoTest_testMutators extends AnnotationTestUtil {

    @Test
    public void testMutators() throws Exception {
        JsonTypeInfo.Value baseValue = JsonTypeInfo.Value.from(
                Anno1.class.getAnnotation(JsonTypeInfo.class));

        assertEquals(JsonTypeInfo.Id.CLASS, baseValue.getIdType());
        assertSame(baseValue, baseValue.withIdType(JsonTypeInfo.Id.CLASS));

        JsonTypeInfo.Value minimalClassId = baseValue.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, minimalClassId.getIdType());

        JsonTypeInfo.Value simpleNameId = baseValue.withIdType(JsonTypeInfo.Id.SIMPLE_NAME);
        assertEquals(JsonTypeInfo.Id.SIMPLE_NAME, simpleNameId.getIdType());

        assertEquals(JsonTypeInfo.As.PROPERTY, baseValue.getInclusionType());
        assertSame(baseValue, baseValue.withInclusionType(JsonTypeInfo.As.PROPERTY));

        JsonTypeInfo.Value externalPropertyInclusion = baseValue.withInclusionType(
                JsonTypeInfo.As.EXTERNAL_PROPERTY);
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY,
                externalPropertyInclusion.getInclusionType());

        assertSame(baseValue, baseValue.withDefaultImpl(null));

        JsonTypeInfo.Value stringDefaultImpl = baseValue.withDefaultImpl(String.class);
        assertEquals(String.class, stringDefaultImpl.getDefaultImpl());

        assertSame(baseValue, baseValue.withIdVisible(true));
        assertFalse(baseValue.withIdVisible(false).getIdVisible());

        assertEquals("foobar", baseValue.withPropertyName("foobar").getPropertyName());
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, visible = true)
    private static class Anno1 {
    }
}
