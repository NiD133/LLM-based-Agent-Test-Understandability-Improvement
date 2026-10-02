package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JacksonInjectTest_testEmpty extends AnnotationTestUtil {

    private final JacksonInject.Value emptyValue = JacksonInject.Value.empty();

    @Test
    public void testEmpty() {
        assertEmptyValueHasNoExplicitSettings();
        assertEmptyValueUsesCallerDefaultForInputHandling();
        assertConstructReturnsEmptyValueForUnsetArguments();
    }

    private void assertEmptyValueHasNoExplicitSettings() {
        assertNull(emptyValue.getId());
        assertNull(emptyValue.getUseInput());
    }

    private void assertEmptyValueUsesCallerDefaultForInputHandling() {
        assertTrue(emptyValue.willUseInput(true));
        assertFalse(emptyValue.willUseInput(false));
    }

    private void assertConstructReturnsEmptyValueForUnsetArguments() {
        assertSame(emptyValue, JacksonInject.Value.construct(null, null, null));
        assertSame(emptyValue, JacksonInject.Value.construct("", null, null));
    }
}
