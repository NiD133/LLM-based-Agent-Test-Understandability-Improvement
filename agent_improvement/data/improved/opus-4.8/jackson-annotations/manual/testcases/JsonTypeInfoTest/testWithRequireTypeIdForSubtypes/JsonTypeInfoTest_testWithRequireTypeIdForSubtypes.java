package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies how {@link JsonTypeInfo.Value#withRequireTypeIdForSubtypes(Boolean)}
 * stores the "require type id for subtypes" flag and how the resulting value is
 * read back via {@link JsonTypeInfo.Value#getRequireTypeIdForSubtypes()}.
 */
public class JsonTypeInfoTest_testWithRequireTypeIdForSubtypes extends AnnotationTestUtil {

    @Test
    public void testWithRequireTypeIdForSubtypes() {
        // A freshly created EMPTY value leaves the flag unset (null).
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        assertNull(emptyValue.getRequireTypeIdForSubtypes(),
                "EMPTY value should have no 'require type id' flag set");

        // Setting the flag to TRUE is reflected by the accessor.
        JsonTypeInfo.Value requiredValue = emptyValue.withRequireTypeIdForSubtypes(Boolean.TRUE);
        assertEquals(Boolean.TRUE, requiredValue.getRequireTypeIdForSubtypes(),
                "Flag should be TRUE after withRequireTypeIdForSubtypes(TRUE)");

        // Setting the flag to FALSE is reflected by the accessor.
        JsonTypeInfo.Value notRequiredValue = emptyValue.withRequireTypeIdForSubtypes(Boolean.FALSE);
        assertEquals(Boolean.FALSE, notRequiredValue.getRequireTypeIdForSubtypes(),
                "Flag should be FALSE after withRequireTypeIdForSubtypes(FALSE)");

        // Passing null clears the flag back to the default (unset) state.
        JsonTypeInfo.Value defaultedValue = emptyValue.withRequireTypeIdForSubtypes(null);
        assertNull(defaultedValue.getRequireTypeIdForSubtypes(),
                "Flag should be unset after withRequireTypeIdForSubtypes(null)");
    }
}
