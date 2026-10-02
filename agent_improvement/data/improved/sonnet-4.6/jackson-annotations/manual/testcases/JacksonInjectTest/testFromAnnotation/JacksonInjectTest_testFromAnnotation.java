package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testFromAnnotation extends AnnotationTestUtil {

    // Helper class whose annotated fields drive the annotation-parsing tests
    private static final class Bogus {
        @JacksonInject(value = "inject", useInput = OptBoolean.FALSE, optional = OptBoolean.FALSE)
        public int field;

        @JacksonInject
        public int vanilla;

        @JacksonInject(optional = OptBoolean.TRUE)
        public int optionalField;
    }

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testFromAnnotation() throws Exception {
        // Passing null to Value.from() must return the singleton EMPTY value
        assertSame(EMPTY, JacksonInject.Value.from(null));

        // A fully-specified annotation maps its attributes into Value correctly
        JacksonInject fullySpecifiedAnn = Bogus.class.getField("field").getAnnotation(JacksonInject.class);
        JacksonInject.Value fullySpecifiedValue = JacksonInject.Value.from(fullySpecifiedAnn);

        assertEquals("inject", fullySpecifiedValue.getId());
        assertEquals(Boolean.FALSE, fullySpecifiedValue.getUseInput());
        assertEquals("JacksonInject.Value(id=inject,useInput=false,optional=false)", fullySpecifiedValue.toString());

        // A value built from a non-default annotation must not equal EMPTY
        assertFalse(fullySpecifiedValue.equals(EMPTY));
        assertFalse(EMPTY.equals(fullySpecifiedValue));

        // JacksonInject.Value must survive a JDK serialize/deserialize round-trip
        byte[] serialized = jdkSerialize(fullySpecifiedValue);
        JacksonInject.Value deserializedValue = jdkDeserialize(serialized);
        assertEquals(fullySpecifiedValue, deserializedValue);

        // A bare @JacksonInject annotation (no attributes set) should produce a Value
        // equal to construct(null, null, null) — specifically, optional defaults to null
        JacksonInject bareAnn = Bogus.class.getField("vanilla").getAnnotation(JacksonInject.class);
        JacksonInject.Value bareValue = JacksonInject.Value.from(bareAnn);
        assertEquals(JacksonInject.Value.construct(null, null, null), bareValue,
                "optional should be `null` by default");

        // An annotation with optional=TRUE should map to construct(null, null, true)
        JacksonInject optionalAnn = Bogus.class.getField("optionalField").getAnnotation(JacksonInject.class);
        JacksonInject.Value optionalValue = JacksonInject.Value.from(optionalAnn);
        assertEquals(JacksonInject.Value.construct(null, null, true), optionalValue);
    }
}
