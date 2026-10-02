package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test38 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        // Build a Value with OBJECT shape, a custom pattern, timezone "7", and radix 4024
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean notLenient = Boolean.valueOf(false);
        JsonFormat.Value baseValue = new JsonFormat.Value(
                "`K22BIe$=Oc|vAr4!T", JsonFormat.Shape.OBJECT, "7", "7",
                emptyFeatures, notLenient, 4024);

        // Array of 9 slots; only the first two are non-null, both pointing to the same object
        JsonFormat.Value[] valueArray = new JsonFormat.Value[9];
        valueArray[0] = baseValue;
        valueArray[1] = valueArray[0]; // same reference as valueArray[0]

        // mergeAll skips null entries; merging a value with itself returns the same instance
        JsonFormat.Value mergedValue = JsonFormat.Value.mergeAll(valueArray);

        assertNotNull(mergedValue);
        // The result is the identical object — merging a value with itself is a no-op
        assertSame(baseValue, mergedValue);

        // All original properties are preserved in the result
        assertEquals("`K22BIe$=Oc|vAr4!T", mergedValue.getPattern());
        assertEquals(JsonFormat.Shape.OBJECT, mergedValue.getShape());
        assertEquals("7", mergedValue.timeZoneAsString());
        assertEquals(4024, mergedValue.getRadix());
    }
}
