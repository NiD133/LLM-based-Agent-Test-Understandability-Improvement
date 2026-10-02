package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testStdMethods extends AnnotationTestUtil {

    private static final String EMPTY_VALUE_DESCRIPTION =
            "JacksonInject.Value(id=null,useInput=null,optional=null)";

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testStdMethods() {
        assertEmptyValueStandardMethods();

        JacksonInject.Value baseline = JacksonInject.Value.construct("value", true, true);
        JacksonInject.Value sameValues = JacksonInject.Value.construct("value", true, true);

        assertEquals(baseline, sameValues);
        assertDifferentFromValuesWithOneDifferentField(baseline);
        assertNotEquals(baseline, "string");
    }

    private void assertEmptyValueStandardMethods() {
        assertEquals(EMPTY_VALUE_DESCRIPTION, EMPTY.toString());

        int hashCode = EMPTY.hashCode();
        if (hashCode == 0) {
            // no fixed value, but should not evaluate to 0
            fail();
        }

        assertEquals(EMPTY, EMPTY);
        assertFalse(EMPTY.equals(null));
        assertFalse(EMPTY.equals("xyz"));
    }

    private void assertDifferentFromValuesWithOneDifferentField(JacksonInject.Value baseline) {
        JacksonInject.Value valueNull = JacksonInject.Value.construct(null, true, true);
        JacksonInject.Value useInputNull = JacksonInject.Value.construct("value", null, true);
        JacksonInject.Value optionalNull = JacksonInject.Value.construct("value", true, null);
        JacksonInject.Value valueNotEqual = JacksonInject.Value.construct("not equal", true, true);
        JacksonInject.Value useInputNotEqual = JacksonInject.Value.construct("value", false, true);
        JacksonInject.Value optionalNotEqual = JacksonInject.Value.construct("value", true, false);

        assertNotEquals(baseline, valueNull);
        assertNotEquals(baseline, useInputNull);
        assertNotEquals(baseline, optionalNull);
        assertNotEquals(baseline, valueNotEqual);
        assertNotEquals(baseline, useInputNotEqual);
        assertNotEquals(baseline, optionalNotEqual);
    }
}
