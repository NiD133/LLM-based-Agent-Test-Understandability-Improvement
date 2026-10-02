package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test21 extends JsonFormat_ESTest_scaffolding {

    // A Value created via forShape(BINARY) should report hasShape()=true
    // and hasNonDefaultRadix()=false because no custom radix was set.
    @Test(timeout = 4000)
    public void test_forShape_withBinaryShape_hasShapeTrue_hasNonDefaultRadixFalse() throws Throwable {
        JsonFormat.Value binaryShapeValue = JsonFormat.Value.forShape(JsonFormat.Shape.BINARY);

        assertTrue(binaryShapeValue.hasShape());
        assertFalse(binaryShapeValue.hasNonDefaultRadix());
    }
}
