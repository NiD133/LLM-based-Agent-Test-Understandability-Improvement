package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test80 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value created via forRadix() should keep the given radix and,
     * because no shape was supplied, report that it has no explicit shape.
     */
    @Test(timeout = 4000)
    public void forRadix_retainsRadixAndHasNoShape() throws Throwable {
        JsonFormat.Value formatValue = JsonFormat.Value.forRadix(0);

        // valueFor() always reports the JsonFormat annotation type.
        assertEquals(JsonFormat.class, formatValue.valueFor());

        assertEquals(0, formatValue.getRadix());
        assertFalse(formatValue.hasShape());
    }
}
