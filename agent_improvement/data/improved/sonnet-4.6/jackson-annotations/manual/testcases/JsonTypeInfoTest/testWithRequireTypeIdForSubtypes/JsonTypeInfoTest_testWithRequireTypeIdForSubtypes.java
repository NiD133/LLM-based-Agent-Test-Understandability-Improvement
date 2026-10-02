package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTypeInfoTest_testWithRequireTypeIdForSubtypes extends AnnotationTestUtil {

    // JsonTypeInfo.Value.EMPTY is the baseline value with all fields at their defaults.
    // getRequireTypeIdForSubtypes() returns null to signal "use global configuration".

    @Test
    public void testEmptyValueHasNullRequireTypeIdForSubtypes() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;

        assertNull(empty.getRequireTypeIdForSubtypes(),
                "EMPTY value should have null requireTypeIdForSubtypes, indicating no per-type override");
    }

    @Test
    public void testWithRequireTypeIdForSubtypesTrue() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value withRequireEnabled = empty.withRequireTypeIdForSubtypes(Boolean.TRUE);

        assertEquals(Boolean.TRUE, withRequireEnabled.getRequireTypeIdForSubtypes(),
                "After setting requireTypeIdForSubtypes=TRUE, getter should return TRUE");
    }

    @Test
    public void testWithRequireTypeIdForSubtypesFalse() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value withRequireDisabled = empty.withRequireTypeIdForSubtypes(Boolean.FALSE);

        assertEquals(Boolean.FALSE, withRequireDisabled.getRequireTypeIdForSubtypes(),
                "After setting requireTypeIdForSubtypes=FALSE, getter should return FALSE");
    }

    @Test
    public void testWithRequireTypeIdForSubtypesNullResetsToDefault() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;

        // Passing null resets the override, deferring to global MapperFeature configuration
        JsonTypeInfo.Value withRequireReset = empty.withRequireTypeIdForSubtypes(null);

        assertNull(withRequireReset.getRequireTypeIdForSubtypes(),
                "Passing null should reset requireTypeIdForSubtypes to null (use global default)");
    }
}
