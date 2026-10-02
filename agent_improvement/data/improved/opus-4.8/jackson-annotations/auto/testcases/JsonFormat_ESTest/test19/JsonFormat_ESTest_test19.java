package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test19 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value created via {@code forRadix} stores only the radix; pattern and
     * shape remain unset (defaults), so {@code hasPattern} and {@code hasShape}
     * both report false.
     */
    @Test(timeout = 4000)
    public void forRadix_storesRadixWithoutPatternOrShape() throws Throwable {
        int radix = -1639;
        JsonFormat.Value formatValue = JsonFormat.Value.forRadix(radix);

        assertEquals(radix, formatValue.getRadix());
        assertFalse("forRadix should not set a pattern", formatValue.hasPattern());
        assertFalse("forRadix should not set a shape", formatValue.hasShape());
    }
}
