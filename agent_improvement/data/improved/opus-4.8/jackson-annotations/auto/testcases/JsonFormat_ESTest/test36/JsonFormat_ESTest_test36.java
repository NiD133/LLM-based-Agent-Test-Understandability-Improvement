package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test36 extends JsonFormat_ESTest_scaffolding {

    /**
     * Merging a Value with a null override should return the original Value
     * unchanged, preserving its custom radix and leaving the shape unset.
     */
    @Test(timeout = 4000)
    public void mergeWithNullOverridePreservesRadixAndShape() throws Throwable {
        int customRadix = -1639;
        JsonFormat.Value valueWithRadix = JsonFormat.Value.forRadix(customRadix);

        JsonFormat.Value merged = JsonFormat.Value.merge(valueWithRadix, (JsonFormat.Value) null);

        assertNotNull(merged);
        assertEquals(customRadix, merged.getRadix());
        assertFalse(merged.hasShape());
    }
}
