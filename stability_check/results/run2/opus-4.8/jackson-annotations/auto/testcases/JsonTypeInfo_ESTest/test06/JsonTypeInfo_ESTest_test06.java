package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test06 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#withWriteTypeIdForDefaultImpl(Boolean)}
     * returns a distinct-but-equal copy when the new flag holds the same logical value
     * (both {@code false}) as the original, only via a different Boolean reference.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Boolean.valueOf(non-"true") yields the cached Boolean.FALSE instance.
        Boolean cachedFalse = Boolean.valueOf("-0VzDY5^*");

        // Original Value carries "false" for writeTypeIdForDefaultImpl via the cached FALSE.
        JsonTypeInfo.Value originalValue = new JsonTypeInfo.Value(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.WRAPPER_OBJECT,
                "-0VzDY5^*",
                Integer.class,
                false,      // idVisible
                cachedFalse, // requireTypeIdForSubtypes
                cachedFalse); // writeTypeIdForDefaultImpl

        // A fresh Boolean(false): equal in value but a different reference from cachedFalse.
        Boolean distinctFalse = new Boolean(false);
        JsonTypeInfo.Value copyValue = originalValue.withWriteTypeIdForDefaultImpl(distinctFalse);

        // Different reference triggers a new instance, yet equals() compares by value.
        assertTrue(originalValue.equals(copyValue));
        assertNotSame(copyValue, originalValue);

        // The flag remains false, so type id is not written for the default impl.
        assertFalse(copyValue.shouldWriteTypeIdForDefaultImpl());
    }
}
