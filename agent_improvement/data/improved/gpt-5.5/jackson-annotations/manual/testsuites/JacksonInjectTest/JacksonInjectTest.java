package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest
    extends AnnotationTestUtil
{
    private static final String INJECT_ID = "inject";
    private static final String VALUE_ID = "value";
    private static final String DIFFERENT_VALUE_ID = "not equal";
    private static final String FACTORY_ID = "name";

    private static final String EMPTY_DESCRIPTION =
            "JacksonInject.Value(id=null,useInput=null,optional=null)";
    private static final String EXPLICIT_DESCRIPTION =
            "JacksonInject.Value(id=inject,useInput=false,optional=false)";

    private final static class Bogus {
        @JacksonInject(value=INJECT_ID, useInput=OptBoolean.FALSE,
                optional=OptBoolean.FALSE)
        public int field;

        @JacksonInject
        public int vanilla;

        @JacksonInject(optional = OptBoolean.TRUE)
        public int optionalField;
    }

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testEmpty()
    {
        assertNull(EMPTY.getId());
        assertNull(EMPTY.getUseInput());
        assertTrue(EMPTY.willUseInput(true));
        assertFalse(EMPTY.willUseInput(false));

        assertSame(EMPTY, JacksonInject.Value.construct(null, null, null));
        assertSame(EMPTY, JacksonInject.Value.construct("", null, null));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        assertSame(EMPTY, JacksonInject.Value.from(null));

        JacksonInject explicitAnnotation = jacksonInjectAnnotation("field");
        JacksonInject.Value explicitValue = JacksonInject.Value.from(explicitAnnotation);
        assertEquals(INJECT_ID, explicitValue.getId());
        assertEquals(Boolean.FALSE, explicitValue.getUseInput());

        assertEquals(EXPLICIT_DESCRIPTION, explicitValue.toString());
        assertFalse(explicitValue.equals(EMPTY));
        assertFalse(EMPTY.equals(explicitValue));

        byte[] serializedValue = jdkSerialize(explicitValue);
        JacksonInject.Value deserializedValue = jdkDeserialize(serializedValue);
        assertEquals(explicitValue, deserializedValue);

        JacksonInject defaultAnnotation = jacksonInjectAnnotation("vanilla");
        JacksonInject.Value defaultValue = JacksonInject.Value.from(defaultAnnotation);
        assertEquals(JacksonInject.Value.construct(null, null, null), defaultValue,
                "optional should be `null` by default");

        JacksonInject optionalAnnotation = jacksonInjectAnnotation("optionalField");
        JacksonInject.Value optionalValue = JacksonInject.Value.from(optionalAnnotation);
        assertEquals(JacksonInject.Value.construct(null, null, true), optionalValue);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testStdMethods() {
        assertEquals(EMPTY_DESCRIPTION, EMPTY.toString());
        assertNonZeroHashCode(EMPTY);

        assertEquals(EMPTY, EMPTY);
        assertFalse(EMPTY.equals(null));
        assertFalse(EMPTY.equals("xyz"));

        JacksonInject.Value referenceValue = JacksonInject.Value.construct(VALUE_ID, true, true);
        JacksonInject.Value sameValue = JacksonInject.Value.construct(VALUE_ID, true, true);
        JacksonInject.Value nullId = JacksonInject.Value.construct(null, true, true);
        JacksonInject.Value nullUseInput = JacksonInject.Value.construct(VALUE_ID, null, true);
        JacksonInject.Value nullOptional = JacksonInject.Value.construct(VALUE_ID, true, null);
        JacksonInject.Value differentId = JacksonInject.Value.construct(DIFFERENT_VALUE_ID, true, true);
        JacksonInject.Value differentUseInput = JacksonInject.Value.construct(VALUE_ID, false, true);
        JacksonInject.Value differentOptional = JacksonInject.Value.construct(VALUE_ID, true, false);
        String string = "string";

        assertEquals(referenceValue, sameValue);
        assertNotEquals(referenceValue, nullId);
        assertNotEquals(referenceValue, nullUseInput);
        assertNotEquals(referenceValue, nullOptional);
        assertNotEquals(referenceValue, differentId);
        assertNotEquals(referenceValue, differentUseInput);
        assertNotEquals(referenceValue, differentOptional);
        assertNotEquals(referenceValue, string);
    }

    @Test
    public void testFactories() throws Exception
    {
        JacksonInject.Value withId = EMPTY.withId(FACTORY_ID);
        assertNotSame(EMPTY, withId);
        assertEquals(FACTORY_ID, withId.getId());
        assertSame(withId, withId.withId(FACTORY_ID));

        JacksonInject.Value withUseInput = withId.withUseInput(Boolean.TRUE);
        assertNotSame(withId, withUseInput);
        assertFalse(withId.equals(withUseInput));
        assertFalse(withUseInput.equals(withId));
        assertSame(withUseInput, withUseInput.withUseInput(Boolean.TRUE));

        JacksonInject.Value withOptional = withId.withOptional(Boolean.TRUE);
        assertNotSame(withId, withOptional);
        assertFalse(withId.equals(withOptional));
        assertFalse(withOptional.equals(withId));
        assertSame(withOptional, withOptional.withOptional(Boolean.TRUE));
        assertTrue(withOptional.getOptional());

        assertNonZeroHashCode(withUseInput);
    }

    private static JacksonInject jacksonInjectAnnotation(String fieldName) throws NoSuchFieldException {
        return Bogus.class.getField(fieldName).getAnnotation(JacksonInject.class);
    }

    private static void assertNonZeroHashCode(JacksonInject.Value value) {
        int hashCode = value.hashCode();
        if (hashCode == 0) {
            fail();
        }
    }
}
