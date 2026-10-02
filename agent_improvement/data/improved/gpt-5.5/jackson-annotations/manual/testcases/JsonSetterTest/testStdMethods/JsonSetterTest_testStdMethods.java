package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonSetterTest_testStdMethods extends AnnotationTestUtil {

    private static final String EMPTY_VALUE_DESCRIPTION =
            "JsonSetter.Value(valueNulls=DEFAULT,contentNulls=DEFAULT)";

    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void testStdMethods() {
        assertEquals(EMPTY_VALUE_DESCRIPTION, emptyValue.toString());

        int hashCode = emptyValue.hashCode();
        if (hashCode == 0) {
            // no fixed value, but should not evaluate to 0
            fail();
        }

        assertEquals(emptyValue, emptyValue);
        assertFalse(emptyValue.equals(null));
        assertFalse(emptyValue.equals("xyz"));
    }
}
