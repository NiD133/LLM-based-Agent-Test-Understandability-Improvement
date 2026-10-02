package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTypeInfoTest_testWithWriteTypeIdForDefaultImpl extends AnnotationTestUtil {

    // [annotations#342]
    @Test
    public void testWithWriteTypeIdForDefaultImpl() {
        JsonTypeInfo.Value defaultValue = JsonTypeInfo.Value.EMPTY;
        assertWriteTypeIdForDefaultImpl(defaultValue, null, true);

        JsonTypeInfo.Value disabledForDefaultImpl =
                defaultValue.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertWriteTypeIdForDefaultImpl(disabledForDefaultImpl, Boolean.FALSE, false);

        JsonTypeInfo.Value enabledForDefaultImpl =
                defaultValue.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertWriteTypeIdForDefaultImpl(enabledForDefaultImpl, Boolean.TRUE, true);

        JsonTypeInfo.Value resetToDefault =
                disabledForDefaultImpl.withWriteTypeIdForDefaultImpl(null);
        assertWriteTypeIdForDefaultImpl(resetToDefault, null, true);

        assertSame(disabledForDefaultImpl,
                disabledForDefaultImpl.withWriteTypeIdForDefaultImpl(Boolean.FALSE));
        assertSame(enabledForDefaultImpl,
                enabledForDefaultImpl.withWriteTypeIdForDefaultImpl(Boolean.TRUE));
    }

    private void assertWriteTypeIdForDefaultImpl(JsonTypeInfo.Value value,
            Boolean expectedSetting, boolean expectedDecision) {
        if (expectedSetting == null) {
            assertNull(value.getWriteTypeIdForDefaultImpl());
        } else {
            assertEquals(expectedSetting, value.getWriteTypeIdForDefaultImpl());
        }
        if (expectedDecision) {
            assertTrue(value.shouldWriteTypeIdForDefaultImpl());
        } else {
            assertFalse(value.shouldWriteTypeIdForDefaultImpl());
        }
    }
}
