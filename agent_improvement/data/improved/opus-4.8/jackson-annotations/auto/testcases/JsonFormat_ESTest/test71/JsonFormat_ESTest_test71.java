package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test71 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value created via forRadix() should retain the given radix and,
     * since no shape was specified, report that it has no shape.
     */
    @Test(timeout = 4000)
    public void forRadix_keepsRadixAndHasNoShape() throws Throwable {
        int customRadix = -1639;
        JsonFormat.Value formatValue = JsonFormat.Value.forRadix(customRadix);

        formatValue.getFeatures();

        assertEquals(customRadix, formatValue.getRadix());
        assertFalse(formatValue.hasShape());
    }
}
