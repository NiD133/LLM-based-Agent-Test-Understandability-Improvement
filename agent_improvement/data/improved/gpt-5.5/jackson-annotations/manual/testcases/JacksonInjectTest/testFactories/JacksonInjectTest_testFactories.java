package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JacksonInjectTest_testFactories extends AnnotationTestUtil {

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testFactories() throws Exception {
        JacksonInject.Value namedValue = EMPTY.withId("name");
        assertNotSame(EMPTY, namedValue);
        assertEquals("name", namedValue.getId());
        assertSame(namedValue, namedValue.withId("name"));

        JacksonInject.Value valueUsingInput = namedValue.withUseInput(Boolean.TRUE);
        assertNotSame(namedValue, valueUsingInput);
        assertFalse(namedValue.equals(valueUsingInput));
        assertFalse(valueUsingInput.equals(namedValue));
        assertSame(valueUsingInput, valueUsingInput.withUseInput(Boolean.TRUE));

        JacksonInject.Value optionalValue = namedValue.withOptional(Boolean.TRUE);
        assertNotSame(namedValue, optionalValue);
        assertFalse(namedValue.equals(optionalValue));
        assertFalse(optionalValue.equals(namedValue));
        assertSame(optionalValue, optionalValue.withOptional(Boolean.TRUE));
        assertTrue(optionalValue.getOptional());

        int hashCode = valueUsingInput.hashCode();
        if (hashCode == 0) {
            // no fixed value, but should not evaluate to 0
            fail();
        }
    }
}
