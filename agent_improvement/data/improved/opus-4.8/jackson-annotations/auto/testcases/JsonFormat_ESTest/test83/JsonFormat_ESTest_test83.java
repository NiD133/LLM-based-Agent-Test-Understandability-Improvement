package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test83 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value built via forRadix(...) should keep the given radix and,
     * since no shape is supplied, report that it has no shape (shape stays ANY).
     */
    @Test(timeout = 4000)
    public void forRadix_storesRadixAndLeavesShapeUnset() throws Throwable {
        JsonFormat.Value valueWithRadix = JsonFormat.Value.forRadix(1);

        assertEquals(1, valueWithRadix.getRadix());
        assertFalse(valueWithRadix.hasShape());
    }
}
