package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test77 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value created via {@code forRadix} stores the given radix and leaves
     * leniency unset and shape at the default {@code ANY} (so {@code hasShape()} is false).
     */
    @Test(timeout = 4000)
    public void forRadix_storesRadixAndLeavesShapeAndLeniencyUnset() throws Throwable {
        int radix = -2473;

        JsonFormat.Value value = JsonFormat.Value.forRadix(radix);

        assertNull("leniency should not be set by forRadix", value.getLenient());
        assertFalse("default shape ANY means hasShape() is false", value.hasShape());
        assertEquals(radix, value.getRadix());
    }
}
