package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testStdMethods extends AnnotationTestUtil {

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testToString() {
        assertEquals("JacksonInject.Value(id=null,useInput=null,optional=null)", EMPTY.toString());
    }

    @Test
    public void testHashCodeIsNonZero() {
        int hashCode = EMPTY.hashCode();
        if (hashCode == 0) {
            // no fixed value, but should not evaluate to 0
            fail();
        }
    }

    @Test
    public void testEqualsReflexivity() {
        assertEquals(EMPTY, EMPTY);
    }

    @Test
    public void testEqualsReturnsFalseForNull() {
        assertFalse(EMPTY.equals(null));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsReturnsFalseForDifferentType() {
        assertFalse(EMPTY.equals("xyz"));
    }

    @Test
    public void testEqualsReturnsTrueForValuesWithIdenticalFields() {
        JacksonInject.Value equals1 = JacksonInject.Value.construct("value", true, true);
        JacksonInject.Value equals2 = JacksonInject.Value.construct("value", true, true);
        assertEquals(equals1, equals2);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsReturnsFalseWhenFieldsDiffer() {
        JacksonInject.Value base         = JacksonInject.Value.construct("value",     true,  true);
        JacksonInject.Value valueNull    = JacksonInject.Value.construct(null,         true,  true);
        JacksonInject.Value useInputNull = JacksonInject.Value.construct("value",     null,  true);
        JacksonInject.Value optionalNull = JacksonInject.Value.construct("value",     true,  null);
        JacksonInject.Value valueNotEqual    = JacksonInject.Value.construct("not equal", true,  true);
        JacksonInject.Value useInputNotEqual = JacksonInject.Value.construct("value",     false, true);
        JacksonInject.Value optionalNotEqual = JacksonInject.Value.construct("value",     true,  false);
        String string = "string";

        assertNotEquals(base, valueNull);
        assertNotEquals(base, useInputNull);
        assertNotEquals(base, optionalNull);
        assertNotEquals(base, valueNotEqual);
        assertNotEquals(base, useInputNotEqual);
        assertNotEquals(base, optionalNotEqual);
        assertNotEquals(base, string);
    }
}
