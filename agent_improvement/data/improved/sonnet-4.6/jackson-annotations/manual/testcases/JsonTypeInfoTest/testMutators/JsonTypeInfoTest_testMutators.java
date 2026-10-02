package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testMutators extends AnnotationTestUtil {

    // Anno1: CLASS id, PROPERTY inclusion (default), visible=true.
    // defaultImpl=JsonTypeInfo.class is an annotation type, normalized to null by Value.construct().
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = JsonTypeInfo.class, requireTypeIdForSubtypes = OptBoolean.TRUE)
    private static final class Anno1 { }

    @Test
    public void testMutators() throws Exception {
        JsonTypeInfo.Value base = JsonTypeInfo.Value.from(Anno1.class.getAnnotation(JsonTypeInfo.class));

        // --- withIdType ---
        // Base value has CLASS; withIdType(CLASS) must return same instance (no change)
        assertEquals(JsonTypeInfo.Id.CLASS, base.getIdType());
        assertSame(base, base.withIdType(JsonTypeInfo.Id.CLASS));

        // Changing to a different id produces a new instance with the updated id
        JsonTypeInfo.Value withMinimalClass = base.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, withMinimalClass.getIdType());

        JsonTypeInfo.Value withSimpleName = base.withIdType(JsonTypeInfo.Id.SIMPLE_NAME);
        assertEquals(JsonTypeInfo.Id.SIMPLE_NAME, withSimpleName.getIdType());

        // --- withInclusionType ---
        // Base value has PROPERTY (default); withInclusionType(PROPERTY) must return same instance
        assertEquals(JsonTypeInfo.As.PROPERTY, base.getInclusionType());
        assertSame(base, base.withInclusionType(JsonTypeInfo.As.PROPERTY));

        // Changing to a different inclusion type produces a new instance
        JsonTypeInfo.Value withExternalProperty = base.withInclusionType(JsonTypeInfo.As.EXTERNAL_PROPERTY);
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, withExternalProperty.getInclusionType());

        // --- withDefaultImpl ---
        // Anno1's defaultImpl is JsonTypeInfo.class (an annotation), normalized to null by construct().
        // withDefaultImpl(null) must return same instance since defaultImpl is already null.
        assertSame(base, base.withDefaultImpl(null));

        // Supplying an actual class produces a new instance carrying that class
        JsonTypeInfo.Value withStringImpl = base.withDefaultImpl(String.class);
        assertEquals(String.class, withStringImpl.getDefaultImpl());

        // --- withIdVisible ---
        // Anno1 has visible=true; withIdVisible(true) must return same instance
        assertSame(base, base.withIdVisible(true));

        // Flipping to false produces a new instance with idVisible=false
        assertFalse(base.withIdVisible(false).getIdVisible());

        // --- withPropertyName ---
        // Supplying a new property name produces a new instance with that name
        assertEquals("foobar", base.withPropertyName("foobar").getPropertyName());
    }
}
