package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testFromAnnotation extends AnnotationTestUtil {

    private static final String FIELD_WITH_EXPLICIT_INJECTION = "field";
    private static final String FIELD_WITH_DEFAULT_INJECTION = "vanilla";
    private static final String FIELD_WITH_OPTIONAL_INJECTION = "optionalField";
    private static final String EXPLICIT_INJECTION_ID = "inject";
    private static final String EXPLICIT_VALUE_DESCRIPTION =
            "JacksonInject.Value(id=inject,useInput=false,optional=false)";

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testFromAnnotation() throws Exception {
        assertSame(EMPTY, JacksonInject.Value.from(null));

        JacksonInject.Value explicitInjection = valueFromBogusField(FIELD_WITH_EXPLICIT_INJECTION);
        assertExplicitInjectionValue(explicitInjection);
        assertRoundTripsThroughJdkSerialization(explicitInjection);

        JacksonInject.Value defaultInjection = valueFromBogusField(FIELD_WITH_DEFAULT_INJECTION);
        assertEquals(JacksonInject.Value.construct(null, null, null), defaultInjection,
                "optional should be `null` by default");

        JacksonInject.Value optionalInjection = valueFromBogusField(FIELD_WITH_OPTIONAL_INJECTION);
        assertEquals(JacksonInject.Value.construct(null, null, true), optionalInjection);
    }

    private JacksonInject.Value valueFromBogusField(String fieldName) throws NoSuchFieldException {
        JacksonInject annotation = Bogus.class.getField(fieldName).getAnnotation(JacksonInject.class);
        return JacksonInject.Value.from(annotation);
    }

    private void assertExplicitInjectionValue(JacksonInject.Value value) {
        assertEquals(EXPLICIT_INJECTION_ID, value.getId());
        assertEquals(Boolean.FALSE, value.getUseInput());
        assertEquals(EXPLICIT_VALUE_DESCRIPTION, value.toString());
        assertFalse(value.equals(EMPTY));
        assertFalse(EMPTY.equals(value));
    }

    private void assertRoundTripsThroughJdkSerialization(JacksonInject.Value value) throws Exception {
        byte[] serializedValue = jdkSerialize(value);
        JacksonInject.Value deserializedValue = jdkDeserialize(serializedValue);
        assertEquals(value, deserializedValue);
    }

    private static class Bogus {
        @JacksonInject(value = EXPLICIT_INJECTION_ID, useInput = OptBoolean.FALSE, optional = OptBoolean.FALSE)
        public Object field;

        @JacksonInject
        public Object vanilla;

        @JacksonInject(optional = OptBoolean.TRUE)
        public Object optionalField;
    }
}
