package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test38 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Value#mergeAll} returns the single non-null
     * Value unchanged when the input array contains only that Value (listed twice)
     * plus null gaps. Merging a Value onto itself is a no-op, so the original
     * instance — with all of its settings intact — is returned as-is.
     */
    @Test(timeout = 4000)
    public void mergeAllReturnsSameInstanceWhenOnlyOneDistinctValue() throws Throwable {
        // A fully-populated format value to merge.
        JsonFormat.Value formatValue = new JsonFormat.Value(
                "`K22BIe$=Oc|vAr4!T",          // pattern
                JsonFormat.Shape.OBJECT,        // shape
                "7",                            // locale string
                "7",                            // timezone string
                JsonFormat.Features.empty(),    // features
                Boolean.FALSE,                  // lenient
                4024);                          // radix

        // Array of 9 slots; only the first two reference the same value, rest are null.
        JsonFormat.Value[] values = new JsonFormat.Value[9];
        values[0] = formatValue;
        values[1] = formatValue;

        JsonFormat.Value merged = JsonFormat.Value.mergeAll(values);

        // Merging a value with itself yields the very same instance, settings preserved.
        assertNotNull(merged);
        assertSame(formatValue, merged);
        assertEquals(4024, merged.getRadix());
        assertEquals("7", merged.timeZoneAsString());
        assertEquals(JsonFormat.Shape.OBJECT, merged.getShape());
        assertEquals("`K22BIe$=Oc|vAr4!T", merged.getPattern());
    }
}
