package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the standard {@code Object} method overrides — {@code toString()},
 * {@code hashCode()} and {@code equals()} — on {@link JacksonInject.Value}.
 */
public class JacksonInjectTest_testStdMethods extends AnnotationTestUtil {

    private final JacksonInject.Value emptyValue = JacksonInject.Value.empty();

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testStdMethods() {
        // toString() lists all three properties, which are null for the empty value.
        assertEquals("JacksonInject.Value(id=null,useInput=null,optional=null)",
                emptyValue.toString());

        // hashCode() has no fixed expected value, but must not collapse to 0.
        assertNotEquals(0, emptyValue.hashCode());

        // equals(): reflexive, and never equal to null or to an unrelated type.
        assertEquals(emptyValue, emptyValue);
        assertFalse(emptyValue.equals(null));
        assertFalse(emptyValue.equals("xyz"));

        // Two values built from identical (id, useInput, optional) arguments are equal.
        JacksonInject.Value value          = JacksonInject.Value.construct("value", true, true);
        JacksonInject.Value identicalValue = JacksonInject.Value.construct("value", true, true);
        assertEquals(value, identicalValue);

        // equals() returns false when any single property differs from the reference value.
        assertNotEquals(value, JacksonInject.Value.construct(null, true, true));        // id is null
        assertNotEquals(value, JacksonInject.Value.construct("value", null, true));     // useInput is null
        assertNotEquals(value, JacksonInject.Value.construct("value", true, null));     // optional is null
        assertNotEquals(value, JacksonInject.Value.construct("not equal", true, true)); // id differs
        assertNotEquals(value, JacksonInject.Value.construct("value", false, true));    // useInput differs
        assertNotEquals(value, JacksonInject.Value.construct("value", true, false));    // optional differs

        // equals() also returns false against an unrelated type.
        assertNotEquals(value, "string");
    }
}
