package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testFactories extends AnnotationTestUtil {

    // Shared baseline: a Value with all fields unset
    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testFactories() throws Exception {

        // --- withId: setting an id on EMPTY produces a new, distinct Value ---
        JacksonInject.Value v = EMPTY.withId("name");
        assertNotSame(EMPTY, v);
        assertEquals("name", v.getId());
        // Calling withId with the same id is a no-op (returns the same instance)
        assertSame(v, v.withId("name"));

        // --- withUseInput: setting useInput=true on v produces a different Value ---
        JacksonInject.Value v2 = v.withUseInput(Boolean.TRUE);
        assertNotSame(v, v2);
        assertNotEquals(v, v2);
        assertNotEquals(v2, v);
        // Calling withUseInput with the same value is a no-op (returns the same instance)
        assertSame(v2, v2.withUseInput(Boolean.TRUE));

        // --- withOptional: setting optional=true on v produces a different Value ---
        JacksonInject.Value v3 = v.withOptional(Boolean.TRUE);
        assertNotSame(v, v3);
        assertNotEquals(v, v3);
        assertNotEquals(v3, v);
        // Calling withOptional with the same value is a no-op (returns the same instance)
        assertSame(v3, v3.withOptional(Boolean.TRUE));
        assertTrue(v3.getOptional());

        // --- hashCode: a Value with fields set must produce a non-zero hash ---
        int hashCode = v2.hashCode();
        assertNotEquals(0, hashCode);
    }
}
